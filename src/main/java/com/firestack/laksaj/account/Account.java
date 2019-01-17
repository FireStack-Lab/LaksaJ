package com.firestack.laksaj.account;

import com.firestack.laksaj.crypto.KDFType;
import com.firestack.laksaj.crypto.KeyTools;
import com.firestack.laksaj.utils.ByteUtil;
import com.firestack.laksaj.utils.HashUtil;
import lombok.Data;

@Data
public class Account {
    private String privateKey;
    private String publicKey;
    private String address;

    public Account(String privateKey) {
        this.privateKey = privateKey;
        this.publicKey = KeyTools.getPublicKeyFromPrivateKey(privateKey, true);
        this.address = KeyTools.getAddressFromPublicKey(this.publicKey);
    }

    public static Account fromFile(String file, String passphrase) throws Exception {
        String privateKey = KeyTools.decryptPrivateKey(file, passphrase);
        return new Account(privateKey);
    }

    public String toFile(String privateKey, String passphrase, KDFType type) throws Exception {
        return KeyTools.encryptPrivateKey(privateKey, passphrase, type);
    }

    public static String toCheckSumAddress(String address) {
        address = address.toLowerCase().replace("0x", "");
        String hash = ByteUtil.byteArrayToHexString(HashUtil.sha256(ByteUtil.hexStringToByteArray(address)));
        StringBuilder ret = new StringBuilder("");
        for (int i = 0; i < address.length(); i++) {
            if (Integer.parseInt(String.valueOf(hash.charAt(i)), 16) >= 8) {
                ret.append(String.valueOf(address.charAt(i)).toUpperCase());
            } else {
                ret.append(String.valueOf(address.charAt(i)));
            }
        }
        return ret.toString();
    }
}
