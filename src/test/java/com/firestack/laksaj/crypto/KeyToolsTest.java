package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.junit.Assert;
import org.junit.Test;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class KeyToolsTest {
    static private final ECNamedCurveParameterSpec secp256k1 = ECNamedCurveTable.getParameterSpec("secp256k1");

    @Test
    public void generateKeyPair() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        ECKeyPair keys = KeyTools.generateKeyPair();
        ECPoint pubKey = secp256k1.getCurve().decodePoint(keys.getPublicKey().toByteArray());
        Assert.assertEquals(keys.getPrivateKey().compareTo(BigInteger.ZERO), 1);
        Assert.assertTrue(pubKey.isValid());
    }

    @Test
    public void generatePrivateKey() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        int i = 0;
        while (i < 10) {
            i++;
            String privateKey = KeyTools.generatePrivateKey();
            if (privateKey.length() != 64) {
                throw new RuntimeException("generator err");
            }
            System.out.println(privateKey);
        }
    }

    @Test
    public void generateRandomBytes() {
        byte[] bytes = KeyTools.generateRandomBytes(32);
        Assert.assertNotNull(bytes);
        Assert.assertTrue(32 == bytes.length);
        System.out.println(ByteUtil.byteArrayToHexString(bytes).toLowerCase());
    }

    @Test
    public void getPublicKeyFromPrivateKey() {
        String privateKey = "24180e6b0c3021aedb8f5a86f75276ee6fc7ff46e67e98e716728326102e91c9";
        String publicKey = KeyTools.getPublicKeyFromPrivateKey(privateKey, false);
        Assert.assertEquals(publicKey.toLowerCase(), "04163fa604c65aebeb7048c5548875c11418d6d106a20a0289d67b59807abdd299d4cf0efcf07e96e576732dae122b9a8ac142214a6bc133b77aa5b79ba46b3e20");
        privateKey = "b776d8f068d11b3c3f5b94db0fb30efea05b73ddb9af1bbd5da8182d94245f0b";
        publicKey = KeyTools.getPublicKeyFromPrivateKey(privateKey, false);
        Assert.assertEquals(publicKey.toLowerCase(), "04cfa555bb63231d167f643f1a23ba66e6ca1458d416ddb9941e95b5fd28df0ac513075403c996efbbc15d187868857e31cf7be4d109b4f8cb3fd40499839f150a");
        privateKey = "e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930";
        System.out.println(KeyTools.getPublicKeyFromPrivateKey(privateKey, true));
        //3b7b12dc9fc38584e4baf021b1f37e4c41096dbde826762b27a7476f360a99c2a
        System.out.println(KeyTools.getPublicKeyFromPrivateKey("a7da3f73462264e44562f003e2dbcf7add319bf4bb0798a83d1009f651a5c803", true));
    }

    @Test
    public void getAddressFromPrivateKey() {
        String privateKey = "e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930";
        String address = KeyTools.getAddressFromPrivateKey(privateKey);
        System.out.println(address.toLowerCase());
    }

    @Test
    public void getAddressFromPublicKey() throws Exception {
        String noPad = "38959e0b7b9c545dc055ab668f8fbaef207e845c590eca4b14993619fff0f723d";
        String padded = "038959e0b7b9c545dc055ab668f8fbaef207e845c590eca4b14993619fff0f723d";

        Assert.assertEquals(KeyTools.getAddressFromPublicKey(noPad), KeyTools.getAddressFromPublicKey(padded));
    }
}
