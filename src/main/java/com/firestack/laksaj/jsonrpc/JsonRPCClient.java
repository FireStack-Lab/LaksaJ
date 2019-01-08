package com.firestack.laksaj.jsonrpc;

import com.firestack.laksaj.blockchain.BlockList;
import com.firestack.laksaj.blockchain.DsBlock;
import com.firestack.laksaj.blockchain.TxBlock;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.Data;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Objects;

public class JsonRPCClient {

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private String url;

    public JsonRPCClient(String url) {
        this.url = url;
    }

    //Blockchain-related methods
    public String getNetworkId() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNetworkId").params(new String[]{""}).build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        Request request = new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
        Response response = client.newCall(request).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);

        return rep.getResult();
    }

    public BlockList getDSBlockListing(int pageNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("DSBlockListing").params(new Integer[]{pageNumber}).build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        Request request = new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
        Response response = client.newCall(request).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }

    public BlockList getTxBlockListing(int pageNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("TxBlockListing").params(new Integer[]{pageNumber}).build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        Request request = new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
        Response response = client.newCall(request).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }


    public DsBlock getDsBlock(String blockNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetDsBlock").params(new String[]{blockNumber}).build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        Request request = new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
        Response response = client.newCall(request).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<DsBlock>>() {
        }.getType();
        Rep<DsBlock> rep = gson.fromJson(resultString, type);
        return rep.getResult();
    }

    public TxBlock getTxBlock(String blockNumber) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTxBlock").params(new String[]{blockNumber}).build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        Request request = new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
        Response response = client.newCall(request).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        return rep.getResult();
    }

    public DsBlock getLatestDsBlock() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetLatestDsBlock").params(new String[]{""}).build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        Request request = new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
        Response response = client.newCall(request).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<DsBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<DsBlock>>() {
        }.getType());
        return rep.getResult();
    }

    public TxBlock getLatestTxBlock() throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetLatestTxBlock").params(new String[]{""}).build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        Request request = new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
        Response response = client.newCall(request).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        return rep.getResult();
    }

    //Account-related methods
    public String getBalance(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetBalance").params(new String[]{address}).build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        Request request = new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
        Response response = client.newCall(request).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        System.out.println(resultString);
        Type type = new TypeToken<Rep<BalanceResult>>() {
        }.getType();
        Rep<BalanceResult> rep = gson.fromJson(resultString, type);
        return rep.getResult().balance;
    }

    @Data
    public static class BalanceResult {
        private String balance;
        private String nonce;
    }
}
