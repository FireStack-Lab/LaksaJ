package com.firestack.laksaj.account;

import com.firestack.laksaj.contract.Contract;
import com.firestack.laksaj.crypto.KDFType;
import com.firestack.laksaj.crypto.KeyTools;
import com.firestack.laksaj.utils.Bech32;
import com.firestack.laksaj.utils.ByteUtil;
import com.firestack.laksaj.utils.HashUtil;
import com.firestack.laksaj.utils.Validation;
import lombok.Data;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

@Data
public class Account {
    private ECKeyPair keys;
    private String address;

    public Account(ECKeyPair keys) throws NoSuchAlgorithmException {
        this.keys = keys;
        this.address = KeyTools.getAddressFromPublicKey(this.keys.getPublicKey().toString(16));
    }

    public Account(String privateKey) throws NoSuchAlgorithmException {
        String publicKey = KeyTools.getPublicKeyFromPrivateKey(privateKey, true);
        this.address = KeyTools.getAddressFromPublicKey(publicKey);
        this.keys = new ECKeyPair(new BigInteger(privateKey, 16), new BigInteger(publicKey, 16));
    }

    ;

    public static Account fromFile(String file, String passphrase) throws Exception {
        String privateKey = KeyTools.decryptPrivateKey(file, passphrase);
        return new Account(privateKey);
    }

    public String toFile(String privateKey, String passphrase, KDFType type) throws Exception {
        return KeyTools.encryptPrivateKey(privateKey, passphrase, type);
    }

    public String getPublicKey() {
        return ByteUtil.byteArrayToHexString(this.keys.getPublicKey().toByteArray());
    }

    public String getPrivateKey() {
        return ByteUtil.byteArrayToHexString(this.keys.getPrivateKey().toByteArray());
    }

    public static String toCheckSumAddress(String address) {
        if (!Validation.isAddress(address)) {
            throw new RuntimeException("not a valid base 16 address");
        }
        address = address.toLowerCase().replace("0x", "");
        String hash = ByteUtil.byteArrayToHexString(HashUtil.sha256(ByteUtil.hexStringToByteArray(address)));
        StringBuilder ret = new StringBuilder("0x");
        BigInteger v = new BigInteger(ByteUtil.hexStringToByteArray(hash));
        for (int i = 0; i < address.length(); i++) {
            if ("1234567890".indexOf(address.charAt(i)) != -1) {
                ret.append(address.charAt(i));
            } else {
                BigInteger checker = v.and(BigInteger.valueOf(2l).pow(255 - 6 * i));
                ret.append(checker.compareTo(BigInteger.valueOf(1l)) < 0 ? String.valueOf(address.charAt(i)).toLowerCase() : String.valueOf(address.charAt(i)).toUpperCase());
            }
        }
        return ret.toString();
    }

    public static String normaliseAddress(String address) throws Exception {
        if (address.equals(Contract.NIL_ADDRESS)) {
            return address;
        }
        if (Validation.isBech32(address)) {
            return Bech32.fromBech32Address(address);
        }

        if (Validation.isAddress(address)) {
            return toCheckSumAddress(address).substring(2);
        }

        throw new Exception("Address format is invalid");
    }


}
