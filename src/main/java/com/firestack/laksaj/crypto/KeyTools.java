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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyTools {

    /**
     * The parameters of the secp256k1 curve that Bitcoin uses.
     */
    public static final ECDomainParameters CURVE;
    private static final X9ECParameters CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1");
    private static final KeyStore keystore = KeyStore.defaultKeyStore();
    private static final Pattern pattern = Pattern.compile("^(0x)?[0-9a-f]");


    static {
        CURVE = new ECDomainParameters(CURVE_PARAMS.getCurve(), CURVE_PARAMS.getG(), CURVE_PARAMS.getN(),
                CURVE_PARAMS.getH());
    }

    public static String generatePrivateKey() {
        return ECKeyPairGenerator.generatePrivateKey();
    }

    public static String getAddressFromPrivateKey(String privateKey) {
        String publicKey = getPublicKeyFromPrivateKey(privateKey, true);
        return getAddressFromPublicKey(publicKey);
    }

    public static boolean isByteString(String address) {
        System.out.println(address);
        Matcher matcher = pattern.matcher(address);
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.print("start:"+matcher.start());
            System.out.println(" end:"+matcher.end());
        }
        return true;
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
        String originAddress = ByteUtil.byteArrayToHexString(data);
        return originAddress.substring(24);
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

    public static String decryptPrivateKey(String file, String passphrase) throws Exception {
        return keystore.decryptPrivateKey(file, passphrase);
    }

    public static String encryptPrivateKey(String privateKey, String passphrase, KDFType type) throws Exception {
        return keystore.encryptPrivateKey(privateKey, passphrase, type);
    }
}
