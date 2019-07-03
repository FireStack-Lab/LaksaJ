package com.firestack.laksaj.crypto;


import org.bouncycastle.crypto.generators.SCrypt;

//http://tools.ietf.org/html/draft-josefsson-scrypt-kdf-01
public class ScryptWrapper {
    public byte[] getDerivedKey(
            byte[] password, byte[] salt, int n, int r, int p, int dkLen) {
        return SCrypt.generate(password, salt, n, r, p, dkLen);
    }
}
