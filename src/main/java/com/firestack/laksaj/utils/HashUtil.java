package com.firestack.laksaj.utils;

import com.google.common.hash.Hashing;

public class HashUtil {

    public static byte[] sha256(byte[] bytes) {
        return Hashing.sha256().hashBytes(bytes).asBytes();
    }

    public static byte[] generateMac(byte[] derivedKey, byte[] cipherText) {
        byte[] result = new byte[16 + cipherText.length];

        System.arraycopy(derivedKey, 16, result, 0, 16);
        System.arraycopy(cipherText, 0, result, 16, cipherText.length);

        return HashUtil.sha256(result);
    }
}
