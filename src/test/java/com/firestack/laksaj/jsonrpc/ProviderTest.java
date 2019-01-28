package com.firestack.laksaj.jsonrpc;

import com.firestack.laksaj.blockchain.*;
import com.firestack.laksaj.transaction.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;


public class ProviderTest {

    @Test
    public void getNetWorkId() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String networkId = client.getNetworkId();
        Assert.assertEquals("TestNet", networkId);
    }

    @Test
    public void getDSBlockListing() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockList blockList = client.getDSBlockListing(1);
        System.out.println(blockList);
        Assert.assertNotNull(blockList);
    }

    @Test
    public void getTxBlockListing() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockList blockList = client.getTxBlockListing(1);
        System.out.println(blockList);
        Assert.assertNotNull(blockList);
    }

    @Test
    public void getBlockchainInfo() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockchainInfo blockchainInfo = client.getBlockchainInfo();
        System.out.println(blockchainInfo);
        Assert.assertNotNull(blockchainInfo);
    }


    @Test
    public void getDsBlock() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        DsBlock dsBlock = client.getDsBlock("1");
        System.out.println(dsBlock);
        Assert.assertNotNull(dsBlock);
        Assert.assertTrue(dsBlock.getHeader().getDifficulty() == 3);
    }

    @Test
    public void getTxBlock() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TxBlock txBlock = client.getTxBlock("40");
        System.out.println(txBlock);
        Assert.assertNotNull(txBlock);
        Assert.assertEquals(3, txBlock.getBody().getMicroBlockInfos().length);
    }

    @Test
    public void getLatestDsBlock() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        DsBlock dsBlock = client.getLatestDsBlock();
        Assert.assertNotNull(dsBlock);
        System.out.println(dsBlock);
    }

    @Test
    public void getLatestTxBlock() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TxBlock txBlock = client.getLatestTxBlock();
        System.out.println(txBlock);
        Assert.assertNotNull(txBlock);
    }

    @Test
    public void getBalance() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        HttpProvider.BalanceResult balance = client.getBalance("E9C49CAF0D0BC9D7C769391E8BDA2028F824CF3D".toLowerCase());
        System.out.println(balance.getBalance());
        Assert.assertNotNull(balance.getBalance());
    }

    @Test
    public void getSmartContractCode() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String code = client.getSmartContractCode("8cb841ef4f1f61d44271e167557e160434bd6d63");
        System.out.println(code);
    }

    @Test
    public void getMinimumGasPrice() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String price = client.getMinimumGasPrice();
        System.out.println(price);

    }


    @Test
    public void getTransaction() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        Transaction transaction = client.getTransaction("0e8d4d5cc5f5a7747fdb004e625da02f177208a93728f72f679ae55e0ba5bc70");
        System.out.println(transaction);
    }

    @Test
    public void getRecentTransactions() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TransactionList transactionList = client.getRecentTransactions();
        System.out.println(transactionList);
    }

    @Test
    public void getSmartContractState() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        List<Contract.State> stateList =  client.getSmartContractState("D6606D02DFF929593312D8D0D36105E376F95AA0");
        System.out.println(stateList);
    }
}
