package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.UUID;

import static com.firestack.laksaj.utils.HashUtil.generateMac;

public class KeyStore {
    private PBKDF2Wrapper pbkdf2Wrapper;
    private ScryptWrapper scryptWrapper;
    private Gson gson = new Gson();

    public KeyStore(PBKDF2Wrapper pbkdf2Wrapper, ScryptWrapper scryptWrapper) {
        this.pbkdf2Wrapper = pbkdf2Wrapper;
        this.scryptWrapper = scryptWrapper;
    }

    public static KeyStore defaultKeyStore() {
        return new KeyStore(new PBKDF2Wrapper(), new ScryptWrapper());
    }

    public byte[] getDerivedKey(byte[] password, KDFParams params) throws UnsupportedEncodingException {
        if (params instanceof PBKDF2Params) {
            PBKDF2Params pbkdf2Params = (PBKDF2Params) params;
            return pbkdf2Wrapper.getDerivedKey(password, ByteUtil.hexStringToByteArray(pbkdf2Params.getSalt()), pbkdf2Params.getCount(), pbkdf2Params.getDkLen());
        } else if (params instanceof ScryptParams) {
            ScryptParams scryptParams = (ScryptParams) params;
            return scryptWrapper.getDerivedKey(password, ByteUtil.hexStringToByteArray(scryptParams.getSalt()), scryptParams.getN(), scryptParams.getR(), scryptParams.getP(), scryptParams.getDkLen());
        } else {
            throw new IllegalArgumentException("unsupport kdf params");
        }
    }

    public String encryptPrivateKey(String privateKey, String passphrase, KDFType type) throws Exception {
        String address = KeyTools.getAddressFromPrivateKey(privateKey);
        byte[] iv = KeyTools.generateRandomBytes(16);
        byte[] saltArray = KeyTools.generateRandomBytes(32);
        String salt = ByteUtil.byteArrayToHexString(saltArray);
        byte[] derivedKey;
        if (type.equals(KDFType.PBKDF2)) {
            PBKDF2Params pbkdf2Params = PBKDF2Params.builder()
                    .salt(salt)
                    .dkLen(32)
                    .count(262144)
                    .build();
            derivedKey = getDerivedKey(passphrase.getBytes(), pbkdf2Params);
        } else {
            ScryptParams scryptParams = ScryptParams.builder()
                    .salt(salt)
                    .dkLen(32)
                    .p(1)
                    .r(8)
                    .n(8192)
                    .build();
            derivedKey = getDerivedKey(passphrase.getBytes(), scryptParams);
        }

        byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);

        //perform cipher operation
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] ciphertext = cipher.doFinal(ByteUtil.hexStringToByteArray(privateKey));
        byte[] mac = generateMac(derivedKey, ciphertext, iv);

        //build struct
        CipherParams cipherParams = CipherParams.builder().iv(ByteUtil.byteArrayToHexString(iv)).build();
        kdfparams kp = new kdfparams(salt);
        Crypto crypto = Crypto.builder()
                .cipher("aes-128-ctr")
                .cipherparams(cipherParams)
                .ciphertext(ByteUtil.byteArrayToHexString(ciphertext))
                .kdf(type.equals(KDFType.PBKDF2) ? "pbkdf2" : "scrypt")
                .kdfparams(kp)
                .mac(ByteUtil.byteArrayToHexString(mac))
                .build();

        KeystoreV3 struct = KeystoreV3.builder()
                .address(address)
                .crypto(crypto)
                .id(UUID.randomUUID().toString())
                .version(3)
                .build();
        return gson.toJson(struct);
    }

    @Data
    @Builder
    public static class KeystoreV3 {
        private String address;
        private Crypto crypto;
        private String id;
        private int version;

    }

    @Data
    @Builder
    public static class Crypto {
        private String cipher;
        private CipherParams cipherparams;
        private String ciphertext;
        private String kdf;
        private kdfparams kdfparams;
        private String mac;

    }

    @Data
    @Builder
    private static class CipherParams {
        private String iv;
    }

    @Data
    private static class kdfparams {
        private int n = 8192;
        private int c = 262144;
        private int r = 8;
        private int p = 1;
        private int dklen = 32;
        private String salt;

        public kdfparams(String salt) {
            this.salt = salt;
        }
    }

    public String decryptPrivateKey(String encryptJson, String passphrase) throws Exception {
        KeystoreV3 keystoreV3 = gson.fromJson(encryptJson, KeystoreV3.class);
        byte[] ciphertext = ByteUtil.hexStringToByteArray(keystoreV3.crypto.ciphertext);
        byte[] iv = ByteUtil.hexStringToByteArray(keystoreV3.crypto.cipherparams.iv);
        kdfparams kp = keystoreV3.crypto.kdfparams;
        String kdf = keystoreV3.crypto.kdf;
        byte[] derivedKey;
        if (kdf.equals("pbkdf2")) {
            PBKDF2Params pbkdf2Params = PBKDF2Params.builder()
                    .salt(kp.salt)
                    .dkLen(32)
                    .count(262144)
                    .build();
            derivedKey = getDerivedKey(passphrase.getBytes(), pbkdf2Params);
        } else {
            ScryptParams scryptParams = ScryptParams.builder()
                    .salt(kp.salt)
                    .dkLen(32)
                    .p(1)
                    .r(8)
                    .n(8192)
                    .build();
            derivedKey = getDerivedKey(passphrase.getBytes(), scryptParams);
        }
        String mac = ByteUtil.byteArrayToHexString(generateMac(derivedKey, ciphertext, iv));
        if (!mac.toUpperCase().equals(keystoreV3.crypto.mac.toUpperCase())) {
            throw new IllegalAccessException("Failed to decrypt.");
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return ByteUtil.byteArrayToHexString(cipher.doFinal(ciphertext));

    }
}
