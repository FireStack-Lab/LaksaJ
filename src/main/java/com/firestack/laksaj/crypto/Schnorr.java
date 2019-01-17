package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;
import com.firestack.laksaj.utils.HashUtil;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Schnorr {

    static final ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");


    static final int PUBKEY_COMPRESSED_SIZE_BYTES = 33;

    static final int ALG_LEN = 16;

    static final int ENT_LEN = 32;

    public static Signature sign(byte[] message, byte[] privateKey, byte[] publicKey) {
        int len = spec.getN().bitLength() / 8;
        HmacDrbg drbg = getDRBG(message);
        BigInteger k = new BigInteger(drbg.nextBytes(len));
        System.out.println("K is: " + ByteUtil.byteArrayToHexString(k.toByteArray()));
//        BigInteger k = new BigInteger(ByteUtil.hexStringToByteArray("4b33d1e9da7e4f5378cd498e6e68a62d91a52c7a4ae38dd871a5feef8b83189b"));
        return trySign(privateKey, publicKey, message, k);
    }

    public static Signature trySign(byte[] privateKey, byte[] publicKey, byte[] message, BigInteger k) {
        //todo add some validate
        ECPoint QPoint = spec.getG().multiply(k);
        BigInteger Q = new BigInteger(QPoint.getEncoded(true));
        List<Integer> m = new ArrayList<>();
        for (byte b : message) {
            m.add((int) b);
        }

        List<Integer> pk = new ArrayList<>();
        for (byte p : publicKey) {
            pk.add((int) p);
        }

        BigInteger r = hash(Q, pk, m).mod(spec.getN());
        BigInteger h = r;
        BigInteger s = h.multiply(new BigInteger(1, privateKey)).mod(spec.getN());
        s = k.subtract(s).mod(spec.getN());
        return Signature.builder().r(r).s(s).build();

    }


    static BigInteger hash(BigInteger q, List<Integer> pubkey, List<Integer> msg) {


        int totalLength = PUBKEY_COMPRESSED_SIZE_BYTES * 2 + msg.size(); // 33 q + 33 pubkey + variable msgLen
        byte[] Q = q.toByteArray();
        byte[] B = new byte[totalLength];

        for (int i = 0; i < totalLength; i++) {
            B[i] = 0;
        }

        for (int i = 0; i < Q.length; i++) {
            B[i] = Q[i];
        }

        for (int i = 0; i < pubkey.size(); i++) {
            B[33 + i] = pubkey.get(i).byteValue();
        }

        for (int i = 0; i < msg.size(); i++) {
            B[66 + i] = msg.get(i).byteValue();
        }

        byte[] hashByte = HashUtil.sha256(B);

        return new BigInteger(1, hashByte);
    }

    static boolean verify(List<Integer> message, BigInteger R, BigInteger S, byte[] publicKey) {
        //todo add some validate
        ECPoint publicKeyPoint = spec.getCurve().decodePoint(publicKey);
        Signature signature = Signature.builder().r(R).s(S).build();
        ECPoint l = publicKeyPoint.multiply(signature.getR());
        ECPoint r = spec.getG().multiply(signature.getS());
        ECPoint QPoint = l.add(r);
        if (QPoint.isInfinity()) {
            throw new IllegalArgumentException("Invalid intermediate point.");
        }
        BigInteger Q = new BigInteger(QPoint.getEncoded(true));
        List<Integer> m = new ArrayList<>();
        List<Integer> pk = new ArrayList<>();
        for (byte p : publicKey) {
            pk.add((int) p);
        }
        BigInteger r1 = hash(Q, pk, message).mod(spec.getN());
        System.out.println(R);
        System.out.println(r1);
        return signature.getR().equals(r1);
    }

    static HmacDrbg getDRBG(byte[] message) {
        byte[] entropy = KeyTools.generateRandomBytes(ENT_LEN);
        byte[] pers = new byte[ALG_LEN + ENT_LEN];

        byte[] tmp = KeyTools.generateRandomBytes(ENT_LEN);
        System.arraycopy(tmp, 0, pers, 0, ENT_LEN);
        System.arraycopy(entropy, 0, pers, 32, ALG_LEN);
        HmacDrbg hmacDrbg = new HmacDrbg(entropy, pers);
        hmacDrbg.hmacDrbgUpdate(message);
        return hmacDrbg;
    }


}
