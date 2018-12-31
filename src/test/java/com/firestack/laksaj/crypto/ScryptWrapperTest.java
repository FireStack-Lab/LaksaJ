package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;
import com.firestack.laksaj.utils.HashUtil;
import org.junit.Assert;
import org.junit.Test;

public class ScryptWrapperTest {
    @Test
    public void generateDerivedScryptKey() {
        ScryptParams params = ScryptParams.builder()
                .dkLen(32)
                .salt("2c37db13a633c5a5e5b8c699109690e33860b7eb43bbc81bbab47d4e9c29f1b9")
                .n(8192)
                .r(8)
                .p(1)
                .build();
        byte[] bytes = ScryptWrapper.generateDerivedScryptKey("stronk_password".getBytes(), ByteUtil.hexStringToByteArray(params.getSalt()), params.getN(), params.getR(), params.getP(), params.getDkLen());
        byte[] macArray = HashUtil.generateMac(bytes,ByteUtil.hexStringToByteArray("ecdf81453d031ac2fa068b7185ddac044fa4632d3b061400d3c07a86510b4823"));
        System.out.println(ByteUtil.byteArrayToHexString(macArray));
        Assert.assertEquals("ed7fa37a4adbc8b7bbe0d43a329a047f89e2dcf7f2dfc96babfe79edd955f7a3",ByteUtil.byteArrayToHexString(macArray).toLowerCase());
    }
}
