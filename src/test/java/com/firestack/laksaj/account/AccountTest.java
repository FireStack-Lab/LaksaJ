package com.firestack.laksaj.account;

import org.junit.Assert;
import org.junit.Test;

public class AccountTest {
    @Test
    public void toCheckSumAddress(){
        String checksum = Account.toCheckSumAddress("DED02FD979FC2E55C0243BD2F52DF022C40ADA1E");
        Assert.assertEquals(checksum,"0xDED02FD979fC2e55c0243Bd2f52DF022C40aDa1E");
    }
}
