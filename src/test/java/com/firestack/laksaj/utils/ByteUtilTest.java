package com.firestack.laksaj.utils;

import org.junit.Assert;
import org.junit.Test;

public class ByteUtilTest {
    @Test
    public void hexStringToByteArray() {
        String hexString = "e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930";
        String byteString = "225,157,5,197,69,37,152,226,76,170,212,160,216,90,73,20,111,123,224,137,81,92,144,90,230,161,158,138,87,138,105,48,";
        byte[] bytes = ByteUtil.hexStringToByteArray(hexString);
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(b & 0xff);
            stringBuilder.append(",");
        }
        System.out.println(stringBuilder.toString());
        Assert.assertEquals(stringBuilder.toString(), byteString);
    }
}
