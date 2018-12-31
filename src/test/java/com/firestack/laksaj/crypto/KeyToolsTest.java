package com.firestack.laksaj.crypto;

import org.junit.Assert;
import org.junit.Test;

public class KeyToolsTest {
    @Test
    public void generateRandomBytes() {
        byte[] bytes = KeyTools.generateRandomBytes(20);
        Assert.assertNotNull(bytes);
        Assert.assertTrue(20 == bytes.length);
    }
}
