package com.firestack.laksaj.jsonrpc;

import com.firestack.laksaj.blockchain.*;
import com.firestack.laksaj.transaction.Transaction;
import okhttp3.OkHttpClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProviderTest {

    @Test
    public void getNetWorkId() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String networkId = client.getNetworkId().getResult();
        Assert.assertEquals("1", networkId);
    }

    @Test
    public void getDSBlockListing() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockList blockList = client.getDSBlockListing(1).getResult();
        System.out.println(blockList);
        Assert.assertNotNull(blockList);
    }

    @Test
    public void getTxBlockListing() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockList blockList = client.getTxBlockListing(1).getResult();
        System.out.println(blockList);
        Assert.assertNotNull(blockList);
    }

    @Test
    public void getBlockchainInfo() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockchainInfo blockchainInfo = client.getBlockchainInfo().getResult();
        System.out.println(blockchainInfo);
        Assert.assertNotNull(blockchainInfo);
    }


    @Test
    public void getDsBlock() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        DsBlock dsBlock = client.getDsBlock("1").getResult();
        System.out.println(dsBlock);
        Assert.assertNotNull(dsBlock);
        Assert.assertTrue(dsBlock.getHeader().getDifficulty() == 3);
    }


    @Test
    public void getNumDSBlocks() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String result = client.getNumDSBlocks().getResult();
        System.out.println(result);
        Assert.assertNotNull(result);
    }


    @Test
    public void getTxBlock() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TxBlock txBlock = client.getTxBlock("116650").getResult();
        System.out.println(txBlock);
    }

    @Test
    public void getLatestDsBlock() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();
        HttpProvider provider = new HttpProvider("https://api.zilliqa.com/", client);
        DsBlock dsBlock = provider.getLatestDsBlock().getResult();
        Assert.assertNotNull(dsBlock);
        System.out.println(dsBlock);
    }

    @Test
    public void getLatestTxBlock() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TxBlock txBlock = client.getLatestTxBlock().getResult();
        System.out.println(txBlock);
        Assert.assertNotNull(txBlock);
    }

    @Test
    public void getBalance() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        HttpProvider.BalanceResult balance = client.getBalance("E9C49CAF0D0BC9D7C769391E8BDA2028F824CF3F".toLowerCase()).getResult();
        Assert.assertNotNull(balance.getBalance());
    }

    @Test
    public void getSmartContractCode() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String code = client.getSmartContractCode("8cb841ef4f1f61d44271e167557e160434bd6d63").getResult().getCode();
        System.out.println(code);
    }

    @Test
    public void getMinimumGasPrice() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String price = client.getMinimumGasPrice().getResult();
        System.out.println(price);

    }


    @Test
    public void getTransaction() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        Transaction transaction = client.getTransaction("ce918e4c77ed40f3a23588bd3c380458b43be168935d468e2e6f680724e71474").getResult();
        System.out.println(transaction);
    }

    @Test
    public void getTransaction32() throws Exception {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        Transaction transaction = client.getTransaction32("ce918e4c77ed40f3a23588bd3c380458b43be168935d468e2e6f680724e71474").getResult();
        System.out.println(transaction);
    }

    @Test
    public void getRecentTransactions() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TransactionList transactionList = client.getRecentTransactions().getResult();
        System.out.println(transactionList);
    }

    @Test
    public void getSmartContractState() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        List<Contract.State> stateList = client.getSmartContractState("D6606D02DFF929593312D8D0D36105E376F95AA0").getResult();
        System.out.println(stateList);
    }

    @Test
    public void getNumTxnsTxEpoch() throws IOException, InterruptedException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String lastEpoch = client.getNumTxnsTxEpoch().getResult();
        List<Contract.State> lastStateList = client.getSmartContractState("D6606D02DFF929593312D8D0D36105E376F95AA0").getResult();

        System.out.println("last epoch is " + lastEpoch);
        System.out.println("last state:" + lastStateList);

        int n = 0;

        while (true) {
            String epoch = client.getNumTxnsTxEpoch().getResult();
            System.out.println(n + "th current epoch is: " + epoch);
            if (!lastEpoch.equals(epoch)) {
                System.out.println("epoch hash changed");
                List<Contract.State> stateList = client.getSmartContractState("D6606D02DFF929593312D8D0D36105E376F95AA0").getResult();
                System.out.println("last state: " + lastStateList);
                System.out.println("current state: " + stateList);
                lastEpoch = epoch;
                lastStateList = stateList;
            }
            Thread.sleep(3000);
            n += 1;
        }
    }
}
