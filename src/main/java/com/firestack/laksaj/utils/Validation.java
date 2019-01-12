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
}
