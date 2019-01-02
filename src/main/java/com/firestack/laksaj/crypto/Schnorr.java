package com.firestack.laksaj.crypto;

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

    public static Signature sign(byte[] privateKey, byte[] publicKey, byte[] message, BigInteger k) {

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
        BigInteger s = h.multiply(new BigInteger(privateKey)).mod(spec.getN());
        s = k.subtract(s).mod(spec.getN());
        System.out.println(r);
        System.out.println(s);
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

        return new BigInteger(hashByte);
    }


}
