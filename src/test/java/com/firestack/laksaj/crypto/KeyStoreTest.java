package com.firestack.laksaj.crypto;

import org.junit.Test;

public class KeyStoreTest {
    @Test
    public void encryptPrivateKey() throws Exception {
        KeyStore keyStore = KeyStore.defaultKeyStore();
        String result = keyStore.encryptPrivateKey("184e14d737356fc4598d371be70ae0d94d61bbd5643d7eb384faa0de7166c010", "dangerous", KDFType.PBKDF2);
        System.out.println(result);
    }
}
