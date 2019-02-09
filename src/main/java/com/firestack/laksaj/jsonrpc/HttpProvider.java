package com.firestack.laksaj.jsonrpc;

import com.firestack.laksaj.blockchain.*;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TransactionPayload;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sun.tools.javac.util.Assert;
import lombok.Data;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class HttpProvider {

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private String url;

    public HttpProvider(String url) {
        this.url = url;
    }

    //Blockchain-related methods
    public Rep<String> getNetworkId() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNetworkId").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);

        return rep;
    }

    public Rep<BlockchainInfo> getBlockchainInfo() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetBlockchainInfo").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockchainInfo>>() {
        }.getType();
        Rep<BlockchainInfo> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<BlockList> getDSBlockListing(int pageNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("DSBlockListing").params(new Integer[]{pageNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<BlockList> getTxBlockListing(int pageNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("TxBlockListing").params(new Integer[]{pageNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getNumDSBlocks() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumDSBlocks").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Double> getDSBlockRate() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetDSBlockRate").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Double>>() {
        }.getType();
        Rep<Double> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<BlockList> getDSBlockListing() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("DSBlockListing").params(new Object[]{1}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<DsBlock> getDsBlock(String blockNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetDsBlock").params(new String[]{blockNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<DsBlock>>() {
        }.getType();
        Rep<DsBlock> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<TxBlock> getTxBlock(String blockNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTxBlock").params(new String[]{blockNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        return rep;
    }

    public Rep<String> getNumTxBlocks() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTxBlocks").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Double> getTxBlockRate() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTxBlockRate").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Double>>() {
        }.getType();
        Rep<Double> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<DsBlock> getLatestDsBlock() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetLatestDsBlock").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<DsBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<DsBlock>>() {
        }.getType());
        return rep;
    }

    public Rep<TxBlock> getLatestTxBlock() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetLatestTxBlock").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        return rep;
    }

    public Rep<String> getNumTransactions() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTransactions").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Integer> getTransactionRate() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransactionRate").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getCurrentMiniEpoch() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetCurrentMiniEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getCurrentDSEpoch() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetCurrentDSEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<Integer> getPrevDifficulty() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetPrevDifficulty").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        return rep;
    }


    //Account-related methods
    public Rep<BalanceResult> getBalance(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetBalance").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        System.out.println(resultString);
        Type type = new TypeToken<Rep<BalanceResult>>() {
        }.getType();
        Rep<BalanceResult> rep = gson.fromJson(resultString, type);
        Assert.checkNonNull(rep.getResult(), "result is null, check your account address!");
        return rep;
    }

    //Contract-related methods todo need test
    public Rep<ContractResult> getSmartContractCode(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractCode").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<ContractResult>>() {
        }.getType();
        Rep<ContractResult> rep = gson.fromJson(resultString, type);
        Assert.checkNonNull(rep.getResult(), "result is null, check your contract address!");
        return rep;
    }

    public Rep<List<Contract>> getSmartContracts(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContracts").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract>>>() {
        }.getType();
        Rep<List<Contract>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<List<Contract.State>> getSmartContractState(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractState").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract.State>>>() {
        }.getType();
        Rep<List<Contract.State>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<List<Contract.State>> getSmartContractInit(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractInit").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract.State>>>() {
        }.getType();
        Rep<List<Contract.State>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    //Transaction-related methods
    public Rep<CreateTxResult> createTransaction(TransactionPayload payload) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("CreateTransaction").params(new Object[]{payload}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<CreateTxResult>>() {
        }.getType();
        Rep<CreateTxResult> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getMinimumGasPrice() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetMinimumGasPrice").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }


    public Rep<Transaction> getTransaction(String hash) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransaction").params(new String[]{hash}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Transaction>>() {
        }.getType();
        Rep<Transaction> rep = gson.fromJson(resultString, type);
        return rep;
    }


    public Rep<TransactionList> getRecentTransactions() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetRecentTransactions").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<TransactionList>>() {
        }.getType();
        Rep<TransactionList> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<List<List<String>>> getTransactionsForTxBlock(String blockNum) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransactionsForTxBlock").params(new String[]{blockNum}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<List<String>>>>() {
        }.getType();
        Rep<List<List<String>>> rep = gson.fromJson(resultString, type);
        return rep;
    }

    public Rep<String> getNumTxnsTxEpoch() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTxnsTxEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep;
    }


    private Request buildRequest(Req req) throws MalformedURLException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        return new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
    }

    @Data
    public static class BalanceResult {
        private String balance;
        private String nonce;
    }

    @Data
    public static class ContractResult {
        private String code;
    }

    @Data
    public static class CreateTxResult {
        private String Info;
        private String TranID;

        @Override
        public String toString() {
            return "CreateTxResult{" +
                    "Info='" + Info + '\'' +
                    ", TranID='" + TranID + '\'' +
                    '}';
        }
    }
}
