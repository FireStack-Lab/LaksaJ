package com.firestack.laksaj.account;

import com.firestack.laksaj.crypto.KDFType;
import com.firestack.laksaj.crypto.KeyTools;
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
}
