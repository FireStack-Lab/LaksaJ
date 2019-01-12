package com.firestack.laksaj.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    //using low case
    public static boolean isByteString(String str, int len) {
        Pattern pattern = Pattern.compile("^(0x)?[0-9a-f]{" + len + "}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isAddress(String address) {
        return isByteString(address, 40);
    }

    public static boolean isPublicKey(String publicKey) {
        return isByteString(publicKey,66);
    }

    public static boolean isPrivateKey(String privateKey) {
        return isByteString(privateKey,64);
    }

    public static boolean isSignature(String signature) {
        return isByteString(signature,128);
    }
}
