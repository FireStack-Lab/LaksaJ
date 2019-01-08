package com.firestack.laksaj.jsonrpc;

import com.firestack.laksaj.blockchain.DsBlock;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class JsonRPCClientTest {

    @Test
    public void getDsBlock() throws IOException {
        JsonRPCClient client = new JsonRPCClient("https://api.zilliqa.com/");
        DsBlock dsBlock = client.getDsBlock("1");
        System.out.println(dsBlock);
        Assert.assertNotNull(dsBlock);
        Assert.assertTrue(dsBlock.getHeader().getDifficulty() == 3);
    }
}
