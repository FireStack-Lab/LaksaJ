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
    public String getNetworkId() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNetworkId").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);

        return rep.getResult();
    }

    public BlockchainInfo getBlockchainInfo() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetBlockchainInfo").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockchainInfo>>() {
        }.getType();
        Rep<BlockchainInfo> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }

    public BlockList getDSBlockListing(int pageNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("DSBlockListing").params(new Integer[]{pageNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }

    public BlockList getTxBlockListing(int pageNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("TxBlockListing").params(new Integer[]{pageNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }


    public DsBlock getDsBlock(String blockNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetDsBlock").params(new String[]{blockNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<DsBlock>>() {
        }.getType();
        Rep<DsBlock> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }

    public TxBlock getTxBlock(String blockNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTxBlock").params(new String[]{blockNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        return rep.getResult();
    }

    public DsBlock getLatestDsBlock() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetLatestDsBlock").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<DsBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<DsBlock>>() {
        }.getType());
        return rep.getResult();
    }

    public TxBlock getLatestTxBlock() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetLatestTxBlock").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        return rep.getResult();
    }

    //Account-related methods
    public BalanceResult getBalance(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetBalance").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BalanceResult>>() {
        }.getType();
        Rep<BalanceResult> rep = gson.fromJson(resultString, type);
        Assert.checkNonNull(rep.getResult(), "result is null, check your account address!");
        return rep.getResult();
    }

    //Contract-related methods todo need test
    public String getSmartContractCode(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractCode").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<ContractResult>>() {
        }.getType();
        Rep<ContractResult> rep = gson.fromJson(resultString, type);
        Assert.checkNonNull(rep.getResult(), "result is null, check your contract address!");
        return rep.getResult().code;
    }

    public List<Contract> getSmartContracts(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContracts").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract>>>() {
        }.getType();
        Rep<List<Contract>> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }

    public List<Contract.State> getSmartContractState(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractState").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract.State>>>() {
        }.getType();
        Rep<List<Contract.State>> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }

    public List<Contract.State> getSmartContractInit(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractInit").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract.State>>>() {
        }.getType();
        Rep<List<Contract.State>> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }

    //Transaction-related methods
    public CreateTxResult createTransaction(TransactionPayload payload) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("CreateTransaction").params(new Object[]{payload}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<CreateTxResult>>() {
        }.getType();
        Rep<CreateTxResult> rep = gson.fromJson(resultString, type);
        return rep.getResult();

    }

    public String getMinimumGasPrice() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetMinimumGasPrice").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }


    public Transaction getTransaction(String hash) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransaction").params(new String[]{hash}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Transaction>>() {
        }.getType();
        Rep<Transaction> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }


    public TransactionList getRecentTransactions() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetRecentTransactions").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<TransactionList>>() {
        }.getType();
        Rep<TransactionList> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }

    public String getNumTxnsTxEpoch() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTxnsTxEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        return rep.getResult();

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
