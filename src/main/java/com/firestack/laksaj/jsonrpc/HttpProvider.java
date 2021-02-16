package com.firestack.laksaj.jsonrpc;

import com.firestack.laksaj.blockchain.*;
import com.firestack.laksaj.exception.ZilliqaAPIException;
import com.firestack.laksaj.transaction.*;
import com.firestack.laksaj.utils.Bech32;
import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.Builder;
import lombok.Data;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class HttpProvider {

    private OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String url;
    private static final Map<Integer, Map<Integer, String>> transactionStatusMap = new HashMap<>();
    private int retry = 3;

    static {
        transactionStatusMap.put(0, new HashMap<Integer, String>() {
            {
                put(0, "Transaction not found");
                put(1, "Pending - Dispatched");
            }
        });

        transactionStatusMap.put(1, new HashMap<Integer, String>() {
            {
                put(2, "Pending - Soft-confirmed (awaiting Tx block generation)");
                put(4, "Pending - Nonce is higher than expected");
                put(5, "Pending - Microblock gas limit exceeded");
                put(6, "Pending - Consensus failure in network");
            }
        });

        transactionStatusMap.put(2, new HashMap<Integer, String>() {
            {
                put(2, "Confirmed");
                put(10, "Rejected - Transaction caused math error");
                put(11, "Rejected - Scilla invocation error");
                put(12, "Rejected - Contract account initialization error");
                put(13, "Rejected - Invalid source account");
                put(14, "Rejected - Gas limit higher than shard gas limit");
                put(15, "Rejected - Unknown transaction type");
                put(16, "Rejected - Transaction sent to wrong shard");
                put(17, "Rejected - Contract & source account cross-shard issue");
                put(18, "Rejected - Code size exceeded limit");
                put(19, "Rejected - Transaction verification failed");
                put(20, "Rejected - Gas limit too low");
                put(21, "Rejected - Insufficient balance");
                put(22, "Rejected - Insufficient gas to invoke Scilla checker");
                put(23, "Rejected - Duplicate transaction exists");
                put(24, "Rejected - Transaction with higher gas price exists");
                put(25, "Rejected - Invalid destination address");
                put(26, "Rejected - Failed to add contract account to state");
                put(27, "Rejected - Nonce is lower than expected");
                put(255, "Rejected - Internal error");
            }
        });
    }

    public HttpProvider(String url) {
        this.url = url;
    }

    public HttpProvider(String url, OkHttpClient client) {
        this.url = url;
        this.client = client;
    }

    public HttpProvider(String url, int retry) {
        this.url = url;
        this.retry = retry;
    }

    //Blockchain-related methods
    public Rep<String> getNetworkId() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNetworkId").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<BlockchainInfo> getBlockchainInfo() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetBlockchainInfo").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockchainInfo>>() {
        }.getType();
        Rep<BlockchainInfo> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<ShardingStructure> getShardingStructure() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetShardingStructure").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<ShardingStructure>>() {
        }.getType();
        Rep<ShardingStructure> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;

    }

    public Rep<BlockList> getDSBlockListing(int pageNumber) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("DSBlockListing").params(new Integer[]{pageNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<BlockList> getTxBlockListing(int pageNumber) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("TxBlockListing").params(new Integer[]{pageNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<String> getNumDSBlocks() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumDSBlocks").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<Double> getDSBlockRate() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetDSBlockRate").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Double>>() {
        }.getType();
        Rep<Double> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<BlockList> getDSBlockListing() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("DSBlockListing").params(new Object[]{1}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BlockList>>() {
        }.getType();
        Rep<BlockList> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<DsBlock> getDsBlock(String blockNumber) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetDsBlock").params(new String[]{blockNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<DsBlock>>() {
        }.getType();
        Rep<DsBlock> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<TxBlock> getTxBlock(String blockNumber) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTxBlock").params(new String[]{blockNumber}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<String> getNumTxBlocks() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTxBlocks").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<Double> getTxBlockRate() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTxBlockRate").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Double>>() {
        }.getType();
        Rep<Double> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<DsBlock> getLatestDsBlock() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetLatestDsBlock").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<DsBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<DsBlock>>() {
        }.getType());
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<TxBlock> getLatestTxBlock() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetLatestTxBlock").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Rep<TxBlock> rep = gson.fromJson(resultString, new TypeToken<Rep<TxBlock>>() {
        }.getType());
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<String> getNumTransactions() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTransactions").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<Integer> getTransactionRate() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransactionRate").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<String> getCurrentMiniEpoch() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetCurrentMiniEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<String> getCurrentDSEpoch() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetCurrentDSEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<Integer> getPrevDifficulty() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetPrevDifficulty").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<Integer> getPrevDSDifficulty() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetPrevDSDifficulty").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Integer>>() {
        }.getType();
        Rep<Integer> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    //Account-related methods
    public Rep<BalanceResult> getBalance(String address) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetBalance").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<BalanceResult>>() {
        }.getType();

        try {
            Rep<BalanceResult> rep = gson.fromJson(resultString, type);
            if (null == rep.getResult()) {
                BalanceResult balanceResult = new BalanceResult();
                balanceResult.setBalance("0");
                balanceResult.setNonce("0");
                rep.setResult(balanceResult);
            }
            return rep;
        } catch (JsonSyntaxException e) {
            throw new IOException("get wrong result: " + resultString);
        }

    }


    public Rep<BalanceResult> getBalanceWithRetry(String address) throws IOException, InterruptedException {
        int leftRetry = this.retry;
        double sleep = 1.0;
        while (leftRetry > 0) {
            try {
                Rep<BalanceResult> status = this.getBalance(address);
                return status;
            } catch (Exception e) {
                TimeUnit.SECONDS.sleep((long) Math.pow(2.0,sleep));
            }
            leftRetry--;
            sleep++;
        }

        throw new IOException("failed after retry");
    }


        public Rep<BalanceResult> getBalance32(String address) throws Exception {
        return getBalance(Bech32.fromBech32Address(address));
    }

    //Contract-related methods todo need test
    public Rep<ContractResult> getSmartContractCode(String address) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractCode").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<ContractResult>>() {
        }.getType();
        Rep<ContractResult> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<List<Contract>> getSmartContracts(String address) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContracts").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract>>>() {
        }.getType();
        Rep<List<Contract>> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<String> getContractAddressFromTransactionID(String address) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetContractAddressFromTransactionID").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public String getSmartContractState(String address) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractState").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        return resultString;
    }

    public String getSmartContractSubState(List<Object> param) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractSubState").params(param.toArray()).build();
        Response response = client.newCall(buildRequest(req)).execute();
        return Objects.requireNonNull(response.body()).string();
    }

    public Rep<List<Contract.State>> getSmartContractInit(String address) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetSmartContractInit").params(new String[]{address}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<Contract.State>>>() {
        }.getType();
        Rep<List<Contract.State>> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    //Transaction-related methods
    public Rep<CreateTxResult> createTransaction(TransactionPayload payload) throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("CreateTransaction").params(new Object[]{payload}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<CreateTxResult>>() {
        }.getType();
        Rep<CreateTxResult> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public List<Transaction> createTransactions(List<Transaction> transactions) throws Exception {
        List<Req> reqs = new ArrayList<>();
        Map<String, Transaction> transactionMap = new HashMap<>();
        for (int i = 0; i < transactions.size(); i++) {
            String index = Integer.toString(i);
            Transaction txn = transactions.get(i);
            transactionMap.put(index, txn);
            TransactionPayload payload = txn.toTransactionPayload();
            Req req = Req.builder().id(index).jsonrpc("2.0").method("CreateTransaction").params(new Object[]{payload}).build();
            reqs.add(req);
        }
        Response response = client.newCall(buildRequests(reqs)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<List<Rep<CreateTxResult>>>() {
        }.getType();
        List<Rep<CreateTxResult>> reps = gson.fromJson(resultString, type);
        for (int i = 0; i < reps.size(); i++) {
            CreateTxResult createTxResult = reps.get(i).getResult();
            if (null == createTxResult) {
                transactionMap.get(reps.get(i).getId()).setStatus(TxStatus.Rejected);
                transactionMap.get(reps.get(i).getId()).setInfo(reps.get(i).getError().message);
            } else if (Strings.isNullOrEmpty(createTxResult.TranID)) {
                transactionMap.get(reps.get(i).getId()).setStatus(TxStatus.Rejected);
                transactionMap.get(reps.get(i).getId()).setInfo(createTxResult.Info);
            } else {
                transactionMap.get(reps.get(i).getId()).setID(createTxResult.TranID);
            }
        }
        return transactions;
    }

    public Rep<String> getMinimumGasPrice() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetMinimumGasPrice").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<TransactionStatus> getTransactionStatus(String hash) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransactionStatus").params(new String[]{hash}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<TransactionStatus>>() {
        }.getType();
        try {
            Rep<TransactionStatus> rep = gson.fromJson(resultString, type);
            if (rep.getResult() == null) {
                throw new IOException("get result error = " + resultString);
            }
            TransactionStatus status = rep.getResult();
            rep.getResult().setInfo(transactionStatusMap.get(status.getModificationState()).get(status.getStatus()));
            return rep;
        } catch (JsonSyntaxException e) {
            throw new IOException("get wrong result: " + resultString);
        }
    }

    public Rep<TransactionStatus> getTransactionStatusWithRetry(String hash) throws IOException, InterruptedException {
        int leftRetry = this.retry;
        double sleep = 1.0;
        while (leftRetry > 0) {
            try {
                Rep<TransactionStatus> status = this.getTransactionStatus(hash);
                return status;
            } catch (Exception e) {
                TimeUnit.SECONDS.sleep((long) Math.pow(2.0,sleep));
            }
            leftRetry--;
            sleep++;
        }

        throw new IOException("failed after retry");
    }

    public Rep<PendingStatus> getPendingTnx(String hash) throws  IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetPendingTxn").params(new String[]{hash}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<PendingStatus>>() {
        }.getType();
        Rep<PendingStatus> rep = gson.fromJson(resultString, type);
        if (rep.getResult() == null) {
            throw new IOException("get result error = " + resultString);
        }
        return rep;
    }

    public Rep<Transaction> getTransaction(String hash) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransaction").params(new String[]{hash}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<Transaction>>() {
        }.getType();
        try {
            Rep<Transaction> rep = gson.fromJson(resultString, type);
            if (rep.getResult() == null) {
                throw new IOException("get result error = " + resultString);
            }
            return rep;
        } catch (JsonSyntaxException e) {
            throw new IOException("get wrong result: " + resultString);
        }
    }

    public Rep<Transaction> getTransaction32(String hash) throws Exception {
        Rep<Transaction> rep = getTransaction(hash);
        rep.getResult().setToAddr(Bech32.toBech32Address(rep.getResult().getToAddr()));
        return rep;
    }


    public Rep<TransactionList> getRecentTransactions() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetRecentTransactions").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<TransactionList>>() {
        }.getType();
        Rep<TransactionList> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<List<List<String>>> getTransactionsForTxBlock(String blockNum) throws IOException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetTransactionsForTxBlock").params(new String[]{blockNum}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<List<List<String>>>>() {
        }.getType();
        Rep<List<List<String>>> rep = gson.fromJson(resultString, type);
        if (rep.getResult() == null) {
            Rep<List<List<String>>> res = new Rep<>();
            res.setJsonrpc("2.0");
            res.setId("1");
            JsonObject jb = gson.fromJson(resultString, JsonObject.class);
            res.setErr(jb.getAsJsonObject("error").get("message").toString());
            return res;
        }
        return rep;
    }


    public Rep<String> getNumTxnsTxEpoch() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTxnsTxEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }

    public Rep<String> getNumTxnsDSEpoch() throws IOException, ZilliqaAPIException {
        Req req = Req.builder().id("1").jsonrpc("2.0").method("GetNumTxnsDSEpoch").params(new String[]{""}).build();
        Response response = client.newCall(buildRequest(req)).execute();
        String resultString = Objects.requireNonNull(response.body()).string();
        Type type = new TypeToken<Rep<String>>() {
        }.getType();
        Rep<String> rep = gson.fromJson(resultString, type);
        if (null == rep.getResult()) {
            Pair pair = parseError(resultString);
            throw new ZilliqaAPIException(pair.getMessage(), pair.getCode());
        }
        return rep;
    }


    private Request buildRequest(Req req) throws MalformedURLException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(req));
        return new Request.Builder()
                .post(body)
                .url(new URL(this.url))
                .build();
    }

    private Request buildRequests(List<Req> reqs) throws MalformedURLException {
        RequestBody body = RequestBody.create(JSON, gson.toJson(reqs));
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
        private String ContractAddress;

        @Override
        public String toString() {
            return "CreateTxResult{" +
                    "Info='" + Info + '\'' +
                    ", TranID='" + TranID + '\'' +
                    ", ContractAddress='" + ContractAddress + '\'' +
                    '}';
        }
    }

    @Data
    public static class CreateTxError {
        private Integer code;
        private Object data;
        private String message;
    }

    @Data
    @Builder
    public static class Pair {
        private String message;
        private int code;


    }

    public Pair parseError(String error) {
        JsonObject root = gson.fromJson(error, JsonObject.class);
        JsonObject err = root.getAsJsonObject("error");
        return Pair.builder().code(err.get("code").getAsInt()).message(err.get("message").getAsString()).build();

    }


}
