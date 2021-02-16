package com.firestack.laksaj.jsonrpc;

import com.firestack.laksaj.blockchain.*;
import com.firestack.laksaj.exception.ZilliqaAPIException;
import com.firestack.laksaj.transaction.PendingStatus;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TransactionStatus;
import okhttp3.OkHttpClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ProviderTest {

    @Test
    public void getNetWorkId() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String networkId = client.getNetworkId().getResult();
        Assert.assertEquals("1", networkId);
    }

    @Test
    public void getDSBlockListing() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockList blockList = client.getDSBlockListing(1).getResult();
        System.out.println(blockList);
        Assert.assertNotNull(blockList);
    }

    @Test
    public void getTxBlockListing() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockList blockList = client.getTxBlockListing(1).getResult();
        System.out.println(blockList);
        Assert.assertNotNull(blockList);
    }

    @Test
    public void getBlockchainInfo() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        BlockchainInfo blockchainInfo = client.getBlockchainInfo().getResult();
        System.out.println(blockchainInfo);
        Assert.assertNotNull(blockchainInfo);
    }


    @Test
    public void getDsBlock() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        DsBlock dsBlock = client.getDsBlock("1").getResult();
        System.out.println(dsBlock);
        Assert.assertNotNull(dsBlock);
        Assert.assertTrue(dsBlock.getHeader().getDifficulty() == 3);
    }


    @Test
    public void getNumDSBlocks() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String result = client.getNumDSBlocks().getResult();
        System.out.println(result);
        Assert.assertNotNull(result);
    }


    @Test
    public void getTxBlock() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TxBlock txBlock = client.getTxBlock("123").getResult();
        System.out.println(txBlock);
    }

    @Test
    public void getLatestDsBlock() throws IOException, ZilliqaAPIException {
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
    public void getLatestTxBlock() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TxBlock txBlock = client.getLatestTxBlock().getResult();
        System.out.println(txBlock);
        Assert.assertNotNull(txBlock);
    }

    @Test
    public void getBalance() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        HttpProvider.BalanceResult balance = client.getBalance("AE9C49CAF0D0BC9D7C769391E8BDA2028F824CF3F".toLowerCase()).getResult();
        Assert.assertNotNull(balance.getBalance());
    }

    @Test
    public void getBalanceWithRetry() throws IOException, InterruptedException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        HttpProvider.BalanceResult balance = client.getBalanceWithRetry("AE9C49CAF0D0BC9D7C769391E8BDA2028F824CF3F".toLowerCase()).getResult();
        Assert.assertNotNull(balance.getBalance());
    }

    @Test
    public void getBalance32() throws Exception {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        HttpProvider.BalanceResult balance = client.getBalance32("zil1z6rpmumewzrmdz44wu9hgvdwrs5xgptlzd6kec").getResult();
        Assert.assertNotNull(balance);
    }

    @Test
    public void getSmartContractCode() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        try {
            String code = client.getSmartContractCode("8cb841ef4f1f61d44271e167557e160434bd6d63").getResult().getCode();
            System.out.println(code);
        } catch (ZilliqaAPIException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getMinimumGasPrice() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String price = client.getMinimumGasPrice().getResult();
        System.out.println(price);

    }

    @Test
    public void getTransactionStatus() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TransactionStatus transaction = client.getTransactionStatus("db89c9998c5ba10b2ebd00116e0b5bd19339a54aed2f5d5bdc8b4e94ebd81f14").getResult();
        System.out.println(transaction);
    }

    @Test
    public void getTransactionStatusWithRetry() throws IOException, InterruptedException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TransactionStatus transaction = client.getTransactionStatusWithRetry("db89c9998c5ba10b2ebd00116e0b5bd19339a54aed2f5d5bdc8b4e94ebd81f14").getResult();
        System.out.println(transaction);
    }

    @Test
    public void getPendingTnx() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        PendingStatus status = client.getPendingTnx("a54d6bccd6cd172baab4e18a2b131c6d870cd778826eba71ff8d3a42819c078f").getResult();
        System.out.println(status);
    }

    @Test
    public void getTransaction() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        Transaction transaction = client.getTransaction("055294ba67b3073d66ef078fb149dfb0490b2d46156479a9f2c9327fb762f4e9").getResult();
        System.out.println(transaction);
    }

    @Test
    public void getTransactionsForTxBlock() throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        Rep<List<List<String>>> rep = client.getTransactionsForTxBlock("120951");
        System.out.println(rep);
    }

    @Test
    public void getTransaction32() throws Exception {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        Transaction transaction = client.getTransaction32("ce918e4c77ed40f3a23588bd3c380458b43be168935d468e2e6f680724e71474").getResult();
        System.out.println(transaction);
    }

    @Test
    public void getRecentTransactions() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        TransactionList transactionList = client.getRecentTransactions().getResult();
        System.out.println(transactionList);
    }

    @Test
    public void getSmartContractState() throws IOException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://mainnet-cashew-api.mainnet.aws.zilliqa.com");
        String stateList = client.getSmartContractState("9611c53BE6d1b32058b2747bdeCECed7e1216793");
        System.out.println(stateList);
    }

    @Test
    public void getSmartContractSubState() throws IOException {
        HttpProvider client = new HttpProvider("https://mainnet-cashew-api.mainnet.aws.zilliqa.com");
        List<Object> param = new ArrayList<>();
        param.add("9611c53BE6d1b32058b2747bdeCECed7e1216793");
        param.add("admins");
        param.add(new ArrayList<>());
        String state = client.getSmartContractSubState(param);
        System.out.println(state);
    }

    @Test
    public void parseError() {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        HttpProvider.Pair pair = client.parseError("{\"error\":{\"code\":-8,\"data\":null,\"message\":\"Address size not appropriate\"},\"id\":\"1\",\"jsonrpc\":\"2.0\"}\n");
        Assert.assertEquals("Address size not appropriate", pair.getMessage());
    }
}
