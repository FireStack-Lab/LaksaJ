package com.firestack.laksaj.crypto;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class KeyToolsTest {
    @Test
    public void generateRandomBytes() {
        byte[] bytes = KeyTools.generateRandomBytes(20);
        Assert.assertNotNull(bytes);
        Assert.assertTrue(20 == bytes.length);
    }
}
