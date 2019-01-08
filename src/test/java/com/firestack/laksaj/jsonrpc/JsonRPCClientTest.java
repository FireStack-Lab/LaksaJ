package com.firestack.laksaj.jsonrpc;

import com.firestack.laksaj.blockchain.DsBlock;
import com.firestack.laksaj.blockchain.TxBlock;
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

    @Test
    public void getTxBlock() throws IOException {
        JsonRPCClient client = new JsonRPCClient("https://api.zilliqa.com/");
        TxBlock txBlock = client.getTxBlock("40");
        System.out.println(txBlock);
        Assert.assertNotNull(txBlock);
        Assert.assertEquals(3,txBlock.getBody().getMicroBlockInfos().length);
    }
}
