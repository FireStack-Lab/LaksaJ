package com.firestack.laksaj.contract;

import com.firestack.laksaj.account.Account;
import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TxStatus;
import com.google.gson.Gson;
import javafx.util.Pair;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.List;


@Data
public class Contract {
    public static String NIL_ADDRESS = "0x0000000000000000000000000000000000000000";

    private ContractFactory contractFactory;
    private Value[] init;
    private String abi;
    private List<com.firestack.laksaj.blockchain.Contract.State> state;
    private String address;
    private String code;
    private ContractStatus contractStatus;

    private Wallet signer;
    private HttpProvider provider;

    public Contract(ContractFactory factory, String code, String abi, String address, Value[] init, List<com.firestack.laksaj.blockchain.Contract.State> state) throws Exception {
        this.contractFactory = factory;
        this.provider = factory.getProvider();
        this.signer = factory.getSigner();
        if (null != address && !address.isEmpty()) {
            this.abi = abi;
            this.address = Account.normaliseAddress(address);
            this.init = init;
            this.state = state;
            this.code = code;
            this.contractStatus = ContractStatus.Deployed;
        } else {
            this.abi = abi;
            this.code = code;
            this.init = init;
            this.contractStatus = ContractStatus.Initialised;
        }
    }

    public Pair<Transaction, Contract> deploy(DeployParams params, int attempts, int interval) throws Exception {
        if (null == this.code || this.code.isEmpty() || null == this.init || this.init.length == 0) {
            throw new IllegalArgumentException("Cannot deploy without code or initialisation parameters.");
        }
        Gson gson = new Gson();
        Transaction transaction = Transaction.builder()
                .ID(params.getID())
                .version(params.getVersion())
                .nonce(params.getNonce())
                .gasPrice(params.getGasPrice())
                .gasLimit(params.getGasLimit())
                .senderPubKey(params.getSenderPubKey())
                .toAddr(NIL_ADDRESS)
                .amount("0")
                .code(this.code.replace("/\\", ""))
                .data(gson.toJson(this.init).replace("/\\", ""))
                .provider(this.provider)
                .build();
        transaction = this.prepareTx(transaction, attempts, interval);
        if (transaction.isRejected()) {
            this.contractStatus = ContractStatus.Rejected;
            Pair<Transaction, Contract> pair = new Pair<>(transaction, this);
            return pair;
        }

        this.contractStatus = ContractStatus.Deployed;
        this.address = ContractFactory.getAddressForContract(transaction);
        Pair<Transaction, Contract> pair = new Pair<>(transaction, this);
        return pair;
    }

    public HttpProvider.CreateTxResult deployWithoutConfirm(DeployParams params) throws Exception {
        if (null == this.code || this.code.isEmpty() || null == this.init || this.init.length == 0) {
            throw new IllegalArgumentException("Cannot deploy without code or initialisation parameters.");
        }
        Gson gson = new Gson();
        Transaction transaction = Transaction.builder()
                .ID(params.getID())
                .version(params.getVersion())
                .nonce(params.getNonce())
                .gasPrice(params.getGasPrice())
                .gasLimit(params.getGasLimit())
                .senderPubKey(params.getSenderPubKey())
                .toAddr(NIL_ADDRESS)
                .amount("0")
                .code(this.code.replace("/\\", ""))
                .data(gson.toJson(this.init).replace("/\\", ""))
                .provider(this.provider)
                .build();
        return this.prepareTx(transaction);
    }

    @Data
    @Builder
    private static class data {
        private String _tag;
        private Value[] params;
    }

    public Transaction call(String transition, Value[] args, CallParams params, int attempts, int interval) throws Exception {
        if (null == this.address || this.address.isEmpty()) {
            throw new IllegalArgumentException("Contract has not been deployed!");
        }

        Gson gson = new Gson();
        Transaction transaction = Transaction.builder()
                .ID(params.getID())
                .version(params.getVersion())
                .nonce(params.getNonce())
                .amount(params.getAmount())
                .gasPrice(params.getGasPrice())
                .gasLimit(params.getGasLimit())
                .senderPubKey(params.getSenderPubKey())
                .data(gson.toJson(data.builder()._tag(transition).params(args).build()))
                .provider(this.provider)
                .toAddr(this.address)
                .code(this.code.replace("/\\", ""))
                .build();
        return this.prepareTx(transaction, attempts, interval);

    }

    public HttpProvider.CreateTxResult prepareTx(Transaction tx) throws Exception {
        tx = signer.sign(tx);
        HttpProvider.CreateTxResult createTxResult = provider.createTransaction(tx.toTransactionPayload()).getResult();
        return createTxResult;
    }

    public Transaction prepareTx(Transaction tx, int attempts, int interval) throws Exception {
        tx = signer.sign(tx);
        try {
            HttpProvider.CreateTxResult createTxResult = provider.createTransaction(tx.toTransactionPayload()).getResult();
            tx.confirm(createTxResult.getTranID(), attempts, interval);
        } catch (IOException e) {
            e.printStackTrace();
            tx.setStatus(TxStatus.Rejected);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tx;
    }

    public boolean isInitialised() {
        return ContractStatus.Initialised.equals(this.contractStatus);
    }

    public boolean isDeployed() {
        return ContractStatus.Deployed.equals(this.contractStatus);
    }

    public boolean isRejected() {
        return ContractStatus.Rejected.equals(this.contractStatus);
    }
}
