package com.firestack.laksaj.utils;

import com.firestack.laksaj.account.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    //using low case
    public static boolean isByteString(String str, int len) {
        Pattern pattern = Pattern.compile("^(0x)?[0-9a-fA-F]{" + len + "}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isBech32(String str) {
        Pattern pattern = Pattern.compile("^zil1[qpzry9x8gf2tvdw0s3jn54khce6mua7l]{38}");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isAddress(String address) {
        return isByteString(address, 40);
    }

    public static boolean isPublicKey(String publicKey) {
        return isByteString(publicKey, 66);
    }

    public static boolean isPrivateKey(String privateKey) {
        return isByteString(privateKey, 64);
    }

    public static boolean isSignature(String signature) {
        return isByteString(signature, 128);
    }


    public static boolean isValidChecksumAddress(String address) {
        return isAddress(address.replace("0x", "")) && Account.toCheckSumAddress(address).equals(address);
    }


    public static String intToHex(int value, int size) {
        String hexVal = Integer.toHexString(value);
        char[] hexRep = new char[hexVal.length()];
        for (int i = 0; i < hexVal.length(); i++) {
            hexRep[i] = hexVal.charAt(i);
        }

        List<Character> hex = new ArrayList<>();

        for (int i = 0; i < size - hexVal.length(); i++) {
            hex.add('0');
        }

        for (int i = 0; i < hexVal.length(); i++) {
            hex.add(hexRep[i]);
        }

        StringBuilder builder = new StringBuilder();
        for (Character c : hex) {
            builder.append(c);
        }
        return builder.toString();

    }
}
