package com.firestack.laksaj.crypto;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.ECGenParameterSpec;

public class ECKeyPairGenerator {

    private static KeyPairGenerator keyPairGenerator;

    private static ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");

    static {
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("EC");
            keyPairGenerator.initialize(ecSpec);
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }

    public static String generatePrivateKey() {
        KeyPair kp = keyPairGenerator.generateKeyPair();
        PrivateKey pvt = kp.getPrivate();
        ECPrivateKey epvt = (ECPrivateKey) pvt;
        return adjustTo64(epvt.getS().toString(16)).toUpperCase();

    }

    private static String adjustTo64(String s) {
        switch (s.length()) {
            case 62:
                return "00" + s;
            case 63:
                return "0" + s;
            case 64:
                return s;
            default:
                throw new IllegalArgumentException("not a valid key: " + s);
        }
    }

    public static void isValidPrivateKey() {

    }
}
