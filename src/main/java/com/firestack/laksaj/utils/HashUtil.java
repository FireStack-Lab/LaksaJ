package com.firestack.laksaj.utils;

import com.google.common.hash.Hashing;

public class HashUtil {

    public static byte[] sha256(byte[] bytes) {
        return Hashing.sha256().hashBytes(bytes).asBytes();
    }

    public static byte[] generateMac(byte[] derivedKey, byte[] cipherText) {
        byte[] bytes = new byte[16 + cipherText.length];
        System.arraycopy(derivedKey, 16, bytes, 0, 16);
        System.arraycopy(cipherText, 0, bytes, 16, cipherText.length);
        return sha256(bytes);
    }
}
