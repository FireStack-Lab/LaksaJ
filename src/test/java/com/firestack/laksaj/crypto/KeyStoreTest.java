package com.firestack.laksaj.crypto;

import org.junit.Assert;
import org.junit.Test;

public class KeyStoreTest {
    @Test
    public void encryptPrivateKey() throws Exception {
        KeyStore keyStore = KeyStore.defaultKeyStore();
        String result = keyStore.encryptPrivateKey("24180e6b0c3021aedb8f5a86f75276ee6fc7ff46e67e98e716728326102e91c9", "xiaohuo", KDFType.PBKDF2);
        System.out.println(result);
        result = keyStore.encryptPrivateKey("24180e6b0c3021aedb8f5a86f75276ee6fc7ff46e67e98e716728326102e91c9", "xiaohuo", KDFType.Scrypt);
        System.out.println(result);
    }

    @Test
    public void decryptPrivateKey() throws Exception {
        KeyStore keyStore = KeyStore.defaultKeyStore();
        String json = "{\"address\":\"B5C2CDD79C37209C3CB59E04B7C4062A8F5D5271\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"cipherparams\":{\"iv\":\"BB77D985DFF840E54EE52510DDF6FE38\"},\"ciphertext\":\"2064375F0A006F70381B180B4B25A139F18F19A40F24ACA9B30AC9E51488DFD4\",\"kdf\":\"pbkdf2\",\"kdfparams\":{\"n\":8192,\"c\":262144,\"r\":8,\"p\":1,\"dklen\":32,\"salt\":[119,19,15,64,53,-57,27,-111,36,105,-72,36,-59,5,-128,77,41,113,-78,-60,66,-102,-123,1,100,-45,-114,80,71,-16,-75,31]},\"mac\":\"8F00ED9E2C84C9387CBC70AE305DBE7B87F87CE106227C381E5EA928A265BB8F\"},\"id\":\"9b5e1a6d-54e1-43a2-8a10-49ab4e41b903\",\"version\":3}\n";
        String privateString = keyStore.decryptPrivateKey(json, "xiaohuo");
        System.out.println(privateString);
        Assert.assertEquals(privateString.toLowerCase(),"24180e6b0c3021aedb8f5a86f75276ee6fc7ff46e67e98e716728326102e91c9");
    }

}
