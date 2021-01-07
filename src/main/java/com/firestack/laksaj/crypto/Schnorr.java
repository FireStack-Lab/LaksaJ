package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.HashUtil;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.prng.SP800SecureRandomBuilder;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.*;
import java.util.Arrays;

public class Schnorr {
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    static private final ECNamedCurveParameterSpec secp256k1 = ECNamedCurveTable.getParameterSpec("secp256k1");

    static private final int PUBKEY_COMPRESSED_SIZE_BYTES = 33;

    static private final byte[] ALG = "Schnorr+SHA256 ".getBytes();

    static private final int ENT_BITS = 256; // 32 bytes of entropy require for the k value

    static ECKeyPair generateKeyPair() throws NoSuchProviderException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", BouncyCastleProvider.PROVIDER_NAME);
        keyPairGenerator.initialize(new ECNamedCurveGenParameterSpec("secp256k1"));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        BCECPrivateKey privateKey = (BCECPrivateKey) keyPair.getPrivate();
        BCECPublicKey publicKey = (BCECPublicKey) keyPair.getPublic();

        return new ECKeyPair(privateKey.getD(), new BigInteger(1, publicKey.getQ().getEncoded(true)));
    }

    public static Signature sign(ECKeyPair kp, byte[] message) {
        SecureRandom drbg = getDRBG(message);

        int len = secp256k1.getN().bitLength() / 8;
        byte[] bytes = new byte[len];
        drbg.nextBytes(bytes);

        Signature signature = null;
        while (signature == null) {
            BigInteger k = new BigInteger(1, bytes);
            signature = trySign(kp, message, k);
        }

        return signature;
    }

    public static Signature trySign(ECKeyPair kp, byte[] msg, BigInteger k) throws IllegalArgumentException {
        BigInteger n = secp256k1.getN();
        BigInteger privateKey = kp.getPrivateKey();
        ECPoint publicKey = secp256k1.getCurve().decodePoint(kp.getPublicKey().toByteArray());

        // 1a. check if private key is 0
        if (privateKey.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Private key must be >= 0");
        }

        // 1b. check if private key is less than curve order, i.e., within [1...n-1]
        if (privateKey.compareTo(n) >= 0) {
            throw new IllegalArgumentException("Private key cannot be greater than curve order");
        }

        // 2. Compute commitment Q = kG, where G is the base point
        ECPoint Q = secp256k1.getG().multiply(k);

        // 3. Compute the challenge r = H(Q || pubKey || msg)
        // mod reduce r by the order of secp256k1, n
        BigInteger r = hash(Q, publicKey, msg).mod(secp256k1.getN());

        if (r.equals(BigInteger.ZERO)) {
            return null;
        }

        //4. Compute s = k - r * prv
        // 4a. Compute r * prv
        BigInteger s = r.multiply(privateKey).mod(n);
        // 4b. Compute s = k - r * prv mod n
        s = k.subtract(s).mod(n);

        if (s.equals(BigInteger.ZERO)) {
            return null;
        }

        return Signature.builder().r(r).s(s).build();
    }

    static private BigInteger hash(ECPoint q, ECPoint pubKey, byte[] msg) {
        // 33 q + 33 pubKey + variable msgLen
        int totalLength = PUBKEY_COMPRESSED_SIZE_BYTES * 2 + msg.length;
        byte[] qCompressed = q.getEncoded(true);
        byte[] pubKeyCompressed = pubKey.getEncoded(true);
        byte[] hashInput = new byte[totalLength];

        Arrays.fill(hashInput, (byte) 0);
        System.arraycopy(qCompressed, 0, hashInput, 0, PUBKEY_COMPRESSED_SIZE_BYTES);
        System.arraycopy(pubKeyCompressed, 0, hashInput, PUBKEY_COMPRESSED_SIZE_BYTES, PUBKEY_COMPRESSED_SIZE_BYTES);
        System.arraycopy(msg, 0, hashInput, PUBKEY_COMPRESSED_SIZE_BYTES * 2, msg.length);

        byte[] hash = HashUtil.sha256(hashInput);

        return new BigInteger(1, hash);
    }

    static boolean verify(byte[] msg, Signature sig, ECPoint publicKey) throws IllegalArgumentException {
        if (sig.getR().equals(BigInteger.ZERO) || sig.getS().equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Invalid R or S value: cannot be zero.");
        }

        if (sig.getR().signum() == -1 || sig.getS().signum() == -1) {
            throw new IllegalArgumentException("Invalid R or S value: cannot be negative.");
        }

        if (!publicKey.getCurve().equals(secp256k1.getCurve())) {
            throw new IllegalArgumentException("The public key must be a point on secp256k1.");
        }

        if (!publicKey.isValid()) {
            throw new IllegalArgumentException("Invalid public key.");
        }

        ECPoint l = publicKey.multiply(sig.getR());
        ECPoint r = secp256k1.getG().multiply(sig.getS());
        ECPoint Q = l.add(r);

        if (Q.isInfinity() || !Q.isValid()) {
            throw new IllegalArgumentException("Invalid intermediate point.");
        }

        BigInteger r1 = hash(Q, publicKey, msg).mod(secp256k1.getN());

        if (r1.equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("Invalid hash.");
        }

        return r1.equals(sig.getR());
    }

    // use custom secure random function to solve blocked issue
    private static SecureRandom getDRBG(byte[] message) {
        SHA256Digest sha256 = new SHA256Digest();
        HMac hMac = new HMac(sha256);
        return new SP800SecureRandomBuilder(new CustomSecureRandom(), false)
                .setEntropyBitsRequired(ENT_BITS)
                .setPersonalizationString(ALG)
                .buildHMAC(hMac, message, true);
    }
}
