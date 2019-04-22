package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;

import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.junit.Assert;
import org.junit.Test;
import org.web3j.utils.Numeric;

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
    }

    @Test
    public void getAddressFromPrivateKey() {
        String privateKey = "e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930";
        String address = KeyTools.getAddressFromPrivateKey(privateKey);
        System.out.println(address.toLowerCase());
    }

    @Test
    public void getAddressFromPublicKey() {
        System.out.println(KeyTools.getAddressFromPublicKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a"));
        System.out.println(KeyTools.getPublicKeyFromPrivateKey("00E5C7DE25D692EBA598912EECDCB1758C1539F39EAAB4D2589F26B98F8A5A2599",true));
    }


}
