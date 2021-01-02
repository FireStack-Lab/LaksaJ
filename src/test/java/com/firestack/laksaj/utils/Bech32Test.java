package com.firestack.laksaj.utils;

import org.junit.Assert;
import org.junit.Test;

public class Bech32Test {
    @Test
    public void toBech32Address() throws Exception {
        String bech32 = Bech32.toBech32Address("0x9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a");
        Assert.assertEquals(bech32.toLowerCase(), "zil1n0lvw9dxh4jcljmzkruvexl69t08zs62ds9ats");
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


        Assert.assertEquals(Bech32.toBech32Address("1d19918a737306218b5cbb3241fcdcbd998c3a72"), "zil1r5verznnwvrzrz6uhveyrlxuhkvccwnju4aehf");
        Assert.assertEquals(Bech32.toBech32Address("cc8ee24773e1b4b28b3cc5596bb9cfc430b48453"), "zil1ej8wy3mnux6t9zeuc4vkhww0csctfpznzt4s76");
        Assert.assertEquals(Bech32.toBech32Address("e14576944443e9aeca6f12b454941884aa122938"), "zil1u9zhd9zyg056ajn0z269f9qcsj4py2fc89ru3d");
        Assert.assertEquals(Bech32.toBech32Address("179361114cbfd53be4d3451edf8148cde4cfe774"), "zil1z7fkzy2vhl2nhexng50dlq2gehjvlem5w7kx8z");
        Assert.assertEquals(Bech32.toBech32Address("5a2b667fdeb6356597681d08f6cd6636aed94784"), "zil1tg4kvl77kc6kt9mgr5y0dntxx6hdj3uy95ash8");
        Assert.assertEquals(Bech32.toBech32Address("537342e5e0a6b402f281e2b4301b89123ae31117"), "zil12de59e0q566q9u5pu26rqxufzgawxyghq0vdk9");
        Assert.assertEquals(Bech32.toBech32Address("5e61d42a952d2df1f4e5cbed7f7d1294e9744a52"), "zil1tesag25495klra89e0kh7lgjjn5hgjjj0qmu8l");
        Assert.assertEquals(Bech32.toBech32Address("5f5db1c18ccde67e513b7f7ae820e569154976ba"), "zil1tawmrsvvehn8u5fm0aawsg89dy25ja46ndsrhq");
    }


    @Test
    public void fromBech32Address() throws Exception {
        System.out.println(Bech32.fromBech32Address("zil18g9n36d4xkhda5r4cwq7q8kutu5atgh3g72qvj"));
        String address = Bech32.fromBech32Address("zil1n0lvw9dxh4jcljmzkruvexl69t08zs62ds9ats");
        Assert.assertEquals(address.toLowerCase(), "9bfec715a6bd658fcb62b0f8cc9bfa2ade71434a");

        Assert.assertEquals(Bech32.fromBech32Address("zil1fwh4ltdguhde9s7nysnp33d5wye6uqpugufkz7").toUpperCase(), "4BAF5FADA8E5DB92C3D3242618C5B47133AE003C");
        Assert.assertEquals(Bech32.fromBech32Address("zil1gjpxry26srx7n008c7nez6zjqrf6p06wur4x3m").toUpperCase(), "448261915A80CDE9BDE7C7A791685200D3A0BF4E");
        Assert.assertEquals(Bech32.fromBech32Address("zil1mmgzlktelsh9tspy80f02t0sytzq4ks79zdnkk").toUpperCase(), "DED02FD979FC2E55C0243BD2F52DF022C40ADA1E");
        Assert.assertEquals(Bech32.fromBech32Address("zil1z0cxucpf004x50zq9ahkf3qk56e3ukrwaty4g8").toUpperCase(), "13F06E60297BEA6A3C402F6F64C416A6B31E586E");
        Assert.assertEquals(Bech32.fromBech32Address("zil1r2gvy5c8c0x8r9v2s0azzw3rvtv9nnenynd33g").toUpperCase(), "1A90C25307C3CC71958A83FA213A2362D859CF33");
        Assert.assertEquals(Bech32.fromBech32Address("zil1vfdt467c0khf4vfg7we6axtg3qfan3wlf9yc6y").toUpperCase(), "625ABAEBD87DAE9AB128F3B3AE99688813D9C5DF");
        Assert.assertEquals(Bech32.fromBech32Address("zil1x6argztlscger3yvswwfkx5ttyf0tq703v7fre").toUpperCase(), "36BA34097F861191C48C839C9B1A8B5912F583CF");
        Assert.assertEquals(Bech32.fromBech32Address("zil16fzn4emvn2r24e2yljnfnk7ut3tk4me6qx08ed").toUpperCase(), "D2453AE76C9A86AAE544FCA699DBDC5C576AEF3A");
        Assert.assertEquals(Bech32.fromBech32Address("zil1wg3qapy50smprrxmckqy2n065wu33nvh35dn0v").toUpperCase(), "72220E84947C36118CDBC580454DFAA3B918CD97");
        Assert.assertEquals(Bech32.fromBech32Address("zil12rujxpxgjtv55wzu5m8xe454pn56x6pedpl554").toUpperCase(), "50F92304C892D94A385CA6CE6CD6950CE9A36839");


        Assert.assertEquals(Bech32.fromBech32Address("zil1r5verznnwvrzrz6uhveyrlxuhkvccwnju4aehf").toLowerCase(), "1d19918a737306218b5cbb3241fcdcbd998c3a72");
        Assert.assertEquals(Bech32.fromBech32Address("zil1ej8wy3mnux6t9zeuc4vkhww0csctfpznzt4s76").toLowerCase(), "cc8ee24773e1b4b28b3cc5596bb9cfc430b48453");
        Assert.assertEquals(Bech32.fromBech32Address("zil1u9zhd9zyg056ajn0z269f9qcsj4py2fc89ru3d").toLowerCase(), "e14576944443e9aeca6f12b454941884aa122938");
        Assert.assertEquals(Bech32.fromBech32Address("zil1z7fkzy2vhl2nhexng50dlq2gehjvlem5w7kx8z").toLowerCase(), "179361114cbfd53be4d3451edf8148cde4cfe774");
        Assert.assertEquals(Bech32.fromBech32Address("zil1tg4kvl77kc6kt9mgr5y0dntxx6hdj3uy95ash8").toLowerCase(), "5a2b667fdeb6356597681d08f6cd6636aed94784");
        Assert.assertEquals(Bech32.fromBech32Address("zil12de59e0q566q9u5pu26rqxufzgawxyghq0vdk9").toLowerCase(), "537342e5e0a6b402f281e2b4301b89123ae31117");
        Assert.assertEquals(Bech32.fromBech32Address("zil1tesag25495klra89e0kh7lgjjn5hgjjj0qmu8l").toLowerCase(), "5e61d42a952d2df1f4e5cbed7f7d1294e9744a52");
        Assert.assertEquals(Bech32.fromBech32Address("zil1tawmrsvvehn8u5fm0aawsg89dy25ja46ndsrhq").toLowerCase(), "5f5db1c18ccde67e513b7f7ae820e569154976ba");

    }
}
