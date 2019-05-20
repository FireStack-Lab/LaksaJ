package com.firestack.laksaj.utils;

import org.junit.Assert;
import org.junit.Test;

public class Bech32Test {
    @Test
    public void toBech32Address() throws Exception {
        String bech32 = Bech32.toBech32Address("0x9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a");
        Assert.assertEquals(bech32.toLowerCase(),"zil1n0lvw9dxh4jcljmzkruvexl69t08zs62ds9ats");
        System.out.println(Bech32.toBech32Address("4BAF5FADA8E5DB92C3D3242618C5B47133AE003C"));
        System.out.println(Bech32.toBech32Address("448261915A80CDE9BDE7C7A791685200D3A0BF4E"));
        System.out.println(Bech32.toBech32Address("DED02FD979FC2E55C0243BD2F52DF022C40ADA1E"));
        System.out.println(Bech32.toBech32Address("13F06E60297BEA6A3C402F6F64C416A6B31E586E"));
        System.out.println(Bech32.toBech32Address("1A90C25307C3CC71958A83FA213A2362D859CF33"));
        System.out.println(Bech32.toBech32Address("625ABAEBD87DAE9AB128F3B3AE99688813D9C5DF"));
        System.out.println(Bech32.toBech32Address("36BA34097F861191C48C839C9B1A8B5912F583CF"));
        System.out.println(Bech32.toBech32Address("D2453AE76C9A86AAE544FCA699DBDC5C576AEF3A"));
        System.out.println(Bech32.toBech32Address("72220E84947C36118CDBC580454DFAA3B918CD97"));
        System.out.println(Bech32.toBech32Address("50F92304C892D94A385CA6CE6CD6950CE9A36839"));
    }


    @Test
    public void fromBech32Address() throws Exception {
        String address = Bech32.fromBech32Address("zil1n0lvw9dxh4jcljmzkruvexl69t08zs62ds9ats");
        Assert.assertEquals(address.toLowerCase(),"9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a");

        Assert.assertEquals( Bech32.fromBech32Address("zil1fwh4ltdguhde9s7nysnp33d5wye6uqpugufkz7").toUpperCase(),"4BAF5FADA8E5DB92C3D3242618C5B47133AE003C");
        Assert.assertEquals( Bech32.fromBech32Address("zil1gjpxry26srx7n008c7nez6zjqrf6p06wur4x3m").toUpperCase(),"448261915A80CDE9BDE7C7A791685200D3A0BF4E");
        Assert.assertEquals( Bech32.fromBech32Address("zil1mmgzlktelsh9tspy80f02t0sytzq4ks79zdnkk").toUpperCase(),"DED02FD979FC2E55C0243BD2F52DF022C40ADA1E");
        Assert.assertEquals( Bech32.fromBech32Address("zil1z0cxucpf004x50zq9ahkf3qk56e3ukrwaty4g8").toUpperCase(),"13F06E60297BEA6A3C402F6F64C416A6B31E586E");
        Assert.assertEquals( Bech32.fromBech32Address("zil1r2gvy5c8c0x8r9v2s0azzw3rvtv9nnenynd33g").toUpperCase(),"1A90C25307C3CC71958A83FA213A2362D859CF33");
        Assert.assertEquals( Bech32.fromBech32Address("zil1vfdt467c0khf4vfg7we6axtg3qfan3wlf9yc6y").toUpperCase(),"625ABAEBD87DAE9AB128F3B3AE99688813D9C5DF");
        Assert.assertEquals( Bech32.fromBech32Address("zil1x6argztlscger3yvswwfkx5ttyf0tq703v7fre").toUpperCase(),"36BA34097F861191C48C839C9B1A8B5912F583CF");
        Assert.assertEquals( Bech32.fromBech32Address("zil16fzn4emvn2r24e2yljnfnk7ut3tk4me6qx08ed").toUpperCase(),"D2453AE76C9A86AAE544FCA699DBDC5C576AEF3A");
        Assert.assertEquals( Bech32.fromBech32Address("zil1wg3qapy50smprrxmckqy2n065wu33nvh35dn0v").toUpperCase(),"72220E84947C36118CDBC580454DFAA3B918CD97");
        Assert.assertEquals( Bech32.fromBech32Address("zil12rujxpxgjtv55wzu5m8xe454pn56x6pedpl554").toUpperCase(),"50F92304C892D94A385CA6CE6CD6950CE9A36839");

    }
}
