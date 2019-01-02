package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;
import com.firestack.laksaj.utils.HashUtil;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.ec.CustomNamedCurves;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyTools {

    /**
     * The parameters of the secp256k1 curve that Bitcoin uses.
     */
    public static final ECDomainParameters CURVE;
    private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");


    static {
        CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(),
                CURVE_PARAMS.getH());
    }

    public static String getAddressFromPrivateKey(String privateKey) {
        //todo
        return "";
    }

    /**
     * @param privateKey hex string without 0x
     * @return
     */
    public static String getPublicKeyFromPrivateKey(String privateKey, boolean compressed) {
        BigInteger bigInteger = new BigInteger(privateKey, 16);
        ECPoint point = getPublicPointFromPrivate(bigInteger);
        return ByteUtil.byteArrayToHexString(point.getEncoded(compressed));
    }

    public static String getAddressFromPublicKey(String publicKey) {
        byte[] data = getAddressFromPublicKey(ByteUtil.hexStringToByteArray(publicKey));
        return ByteUtil.byteArrayToHexString(data);
    }

    public static byte[] getAddressFromPublicKey(byte[] publicKey) {
        return HashUtil.sha256(publicKey);
    }

    public static boolean verifyPrivateKey(String privateKey) {
        //todo
        return true;
    }

    public static byte[] generateRandomBytes(int size) {
        byte[] bytes = new byte[size];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    private static ECPoint getPublicPointFromPrivate(BigInteger privateKeyPoint) {
        if (privateKeyPoint.bitLength() > CURVE.getN().bitLength()) {
            privateKeyPoint = privateKeyPoint.mod(CURVE.getN());
        }
        return new FixedPointCombMultiplier().multiply(CURVE.getG(), privateKeyPoint);
    }
}
