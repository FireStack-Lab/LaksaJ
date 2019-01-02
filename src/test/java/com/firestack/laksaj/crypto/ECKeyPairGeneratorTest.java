package com.firestack.laksaj.crypto;

import org.junit.Test;


public class ECKeyPairGeneratorTest {
    @Test
    public void generaterPrivateKey() {
        String privateKey = ECKeyPairGenerator.generatePrivateKey();
        System.out.println(privateKey.toLowerCase());
    }
}