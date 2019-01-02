package com.firestack.laksaj.account;

import com.firestack.laksaj.crypto.KeyTools;

public class Account {
    private String privateKey;
    private String publicKey;
    private String address;

    public Account(String privateKey) {
        this.privateKey = privateKey;
        this.publicKey = KeyTools.getPublicKeyFromPrivateKey(privateKey,false);
        this.address = KeyTools.getAddressFromPublicKey(this.publicKey);
    }
}
