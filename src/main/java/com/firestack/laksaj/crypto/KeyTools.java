package com.firestack.laksaj.crypto;

import java.security.SecureRandom;

public class KeyTools {

    public static String getAddressFromPrivateKey(String privateKey) {
        //todo
        return "";
    }

    public static String getPublicKeyFromPrivateKey(String privateKey) {
        //todo
        return "";
    }

    public static String getAddressFromPublicKey(String publicKey) {
        //todo
        return "";
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
}
