package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;
import com.firestack.laksaj.utils.HashUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class PBKDF2WrapperTest {
    @Test
    public void generateDerivedScryptKey() throws UnsupportedEncodingException {
        PBKDF2Params params = PBKDF2Params
                .builder()
                .salt("0f2274f6c0daf36d5822d97985be5a3d881d11e2e741bad4e038a099eecc3b6d")
                .count(262144)
                .dkLen(32)
                .build();
        byte[] bytes = PBKDF2Wrapper.generateDerivedScryptKey("stronk_password",
                ByteUtil.hexStringToByteArray(params.getSalt()), params.getCount(), params.getDkLen());
        byte[] macArray = HashUtil.generateMac(bytes,ByteUtil.hexStringToByteArray("dc55047d51f795509ffb6969db837a4481887ccfb6bfb7c259fb77b19078c2a4"));
        System.out.println(ByteUtil.byteArrayToHexString(macArray));
        Assert.assertEquals(ByteUtil.byteArrayToHexString(macArray).toLowerCase(),"dedc361c53c421974c2811f7f989bc530aebf9a90c487b4161e0e54ae6faba31");

    }
}
