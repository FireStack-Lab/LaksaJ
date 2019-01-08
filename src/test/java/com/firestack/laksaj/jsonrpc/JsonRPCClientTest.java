package com.firestack.laksaj.jsonrpc;

import com.firestack.laksaj.blockchain.BlockList;
import com.firestack.laksaj.blockchain.DsBlock;
import com.firestack.laksaj.blockchain.TxBlock;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class JsonRPCClientTest {

    @Test
    public void getNetWorkId() throws IOException {
        JsonRPCClient client = new JsonRPCClient("https://api.zilliqa.com/");
        String networkId = client.getNetworkId();
        Assert.assertEquals("TestNet", networkId);
    }

    @Test
    public void getDSBlockListing() throws IOException {
        JsonRPCClient client = new JsonRPCClient("https://api.zilliqa.com/");
        BlockList blockList = client.getDSBlockListing(1);
        System.out.println(blockList);
        Assert.assertNotNull(blockList);
    }

    @Test
    public void getTxBlockListing() throws IOException {
        JsonRPCClient client = new JsonRPCClient("https://api.zilliqa.com/");
        BlockList blockList = client.getTxBlockListing(1);
        System.out.println(blockList);
        Assert.assertNotNull(blockList);
    }


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
        Assert.assertEquals(3, txBlock.getBody().getMicroBlockInfos().length);
    }

    @Test
    public void getLatestDsBlock() throws IOException {
        JsonRPCClient client = new JsonRPCClient("https://api.zilliqa.com/");
        DsBlock dsBlock = client.getLatestDsBlock();
        Assert.assertNotNull(dsBlock);
        System.out.println(dsBlock);
    }

    @Test
    public void getLatestTxBlock() throws IOException {
        JsonRPCClient client = new JsonRPCClient("https://api.zilliqa.com/");
        TxBlock txBlock = client.getLatestTxBlock();
        System.out.println(txBlock);
        Assert.assertNotNull(txBlock);
    }

    @Test
    public void getBalance() throws IOException {
        JsonRPCClient client = new JsonRPCClient("https://api.zilliqa.com/");
        String balance = client.getBalance("E9C49CAF0D0BC9D7C769391E8BDA2028F824CF3D".toLowerCase());
        System.out.println(balance);
        Assert.assertNotNull(balance);
    }
}
