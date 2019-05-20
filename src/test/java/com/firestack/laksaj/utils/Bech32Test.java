package com.firestack.laksaj.utils;

import org.junit.Assert;
import org.junit.Test;

public class Bech32Test {
    @Test
    public void toBech32Address() throws Exception {
        String bech32 = Bech32.toBech32Address("0x9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a");
        Assert.assertEquals(bech32.toLowerCase(),"zil1n0lvw9dxh4jcljmzkruvexl69t08zs62ds9ats");
    }


    @Test
    public void fromBech32Address() throws Exception {
        String address = Bech32.fromBech32Address("zil1n0lvw9dxh4jcljmzkruvexl69t08zs62ds9ats");
        Assert.assertEquals(address.toLowerCase(),"9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a");

    }
}
