package com.firestack.laksaj.crypto;

import org.junit.Assert;
import org.junit.Test;

public class KeyToolsTest {
    @Test
    public void generateRandomBytes() {
        byte[] bytes = KeyTools.generateRandomBytes(20);
        Assert.assertNotNull(bytes);
        Assert.assertTrue(20 == bytes.length);
    }

    @Test
    public void getPublicKeyFromPrivateKey() {
        String privateKey = "24180e6b0c3021aedb8f5a86f75276ee6fc7ff46e67e98e716728326102e91c9";
        String publicKey = KeyTools.getPublicKeyFromPrivateKey(privateKey,false);
        Assert.assertEquals(publicKey.toLowerCase(),"04163fa604c65aebeb7048c5548875c11418d6d106a20a0289d67b59807abdd299d4cf0efcf07e96e576732dae122b9a8ac142214a6bc133b77aa5b79ba46b3e20");
        privateKey = "b776d8f068d11b3c3f5b94db0fb30efea05b73ddb9af1bbd5da8182d94245f0b";
        publicKey = KeyTools.getPublicKeyFromPrivateKey(privateKey,false);
        Assert.assertEquals(publicKey.toLowerCase(),"04cfa555bb63231d167f643f1a23ba66e6ca1458d416ddb9941e95b5fd28df0ac513075403c996efbbc15d187868857e31cf7be4d109b4f8cb3fd40499839f150a");

    }
}
