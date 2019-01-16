package com.firestack.laksaj.account;

import org.junit.Assert;
import org.junit.Test;

public class AccountTest {
    @Test
    public void toCheckSumAddress(){
        String checksum = Account.toCheckSumAddress("448261915A80CDE9BDE7C7A791685200D3A0BF4E");
        Assert.assertEquals(checksum,"0x448261915a80CDe9bde7C7A791685200d3A0BF4e");
    }
}
