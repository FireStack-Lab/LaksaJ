package com.firestack.laksaj.crypto;

import org.junit.Test;

public class KeyStoreTest {
    @Test
    public void encryptPrivateKey() throws Exception {
        KeyStore keyStore = KeyStore.defaultKeyStore();
        String result = keyStore.encryptPrivateKey("24180e6b0c3021aedb8f5a86f75276ee6fc7ff46e67e98e716728326102e91c9","xiaohuo",KDFType.PBKDF2);
        System.out.println(result);
        result = keyStore.encryptPrivateKey("24180e6b0c3021aedb8f5a86f75276ee6fc7ff46e67e98e716728326102e91c9","xiaohuo",KDFType.Scrypt);
        System.out.println(result);
    }
}
