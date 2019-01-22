package com.firestack.laksaj.utils;

import org.junit.Assert;
import org.junit.Test;

public class ValidationTest {

    @Test
    public void isByteString() {
        boolean result = Validation.isByteString("e9c49caf0d0bc9d7c769391e8bda2028f824cf3d", 40);
        Assert.assertTrue(result);
        result = Validation.isByteString("e9c49caf0d0bc9d7c769391e8bda2028f824cf3", 40);
        Assert.assertFalse(result);
        result = Validation.isByteString("e9c49caf0d0bc9d7c76g391e8bda2028f824cf3d", 40);
        Assert.assertFalse(result);
    }

    @Test
    public void isValidChecksumAddress() {
        Assert.assertTrue(Validation.isValidChecksumAddress("0x4BAF5faDA8e5Db92C3d3242618c5B47133AE003C"));
        Assert.assertFalse(Validation.isValidChecksumAddress("0x4BAF5FaDA8e5Db92C3d3242618c5B47133AE003C"));
    }

}
