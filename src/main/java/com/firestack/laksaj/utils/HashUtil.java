package com.firestack.laksaj.utils;

import com.google.common.hash.Hashing;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class HashUtil {

    public static String ALGO_IDENTIFIER = "aes-128-ctr";


    public static byte[] hmacSha256(byte[] derivedKey, byte[] bytes) {
        return Hashing.hmacSha256(derivedKey).hashBytes(bytes).asBytes();
    }

    public static byte[] sha256(byte[] bytes) {
        return Hashing.sha256().hashBytes(bytes).asBytes();
    }

    public static byte[] generateMac(byte[] derivedKey, byte[] cipherText, byte[] iv) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(16 + cipherText.length + iv.length + ALGO_IDENTIFIER.getBytes().length);
        byteBuffer.put(Arrays.copyOfRange(derivedKey, 16, 32));
        byteBuffer.put(cipherText);
        byteBuffer.put(iv);
        byteBuffer.put(ALGO_IDENTIFIER.getBytes());
        return HashUtil.hmacSha256(derivedKey, byteBuffer.array());
    }

    public static byte[] encode(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return sha256_HMAC.doFinal(data.getBytes("UTF-8"));
    }
}
