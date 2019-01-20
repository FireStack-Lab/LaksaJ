package com.firestack.laksaj.transaction;


import com.firestack.laksaj.account.Account;
import com.firestack.laksaj.blockchain.TransactionReceipt;
import com.firestack.laksaj.jsonrpc.Provider;
import com.firestack.laksaj.utils.TransactionUtil;
import com.google.gson.Gson;
import lombok.Data;
import lombok.experimental.Builder;

import java.io.IOException;

@Data
@Builder
public class Transaction {
    private String ID;
    private String version;
    private String nonce;
    private String amount;
    private String gasPrice;
    private String gasLimit;
    private String signature;
    private TransactionReceipt receipt;
    private String senderPubKey;
    private String toAddr;
    private String code;
    private String data;

    private Provider provider;
    private TxStatus status;

    public TxParams toTransactionParam() {
        return TxParams.builder()
                .ID(this.ID)
                .version(this.version)
                .nonce(this.nonce)
                .amount(this.amount)
                .gasPrice(this.gasPrice)
                .gasLimit(this.gasLimit)
                .signature(this.signature)
                .receipt(this.receipt)
                .senderPubKey(this.senderPubKey.toLowerCase())
                .toAddr(this.toAddr.toLowerCase())
                .code(this.code)
                .data(this.data)
                .build();
    }

    public TransactionPayload toTransactionPayload() {
        return TransactionPayload.builder()
                .version(Integer.parseInt(this.version))
                .nonce(Integer.valueOf(this.nonce))
                .toAddr(Account.toCheckSumAddress(this.toAddr).substring(2))
                .amount(this.amount)
                .pubKey(this.senderPubKey.toLowerCase())
                .gasPrice(this.gasPrice)
                .gasLimit(this.gasLimit)
                .code(this.code)
                .data(this.data)
                .signature(this.signature.toLowerCase())
                .build();
    }

    public byte[] bytes() {
        TxParams txParams = toTransactionParam();
        TransactionUtil util = new TransactionUtil();
        Gson gson = new Gson();
        System.out.println("param is: " + gson.toJson(txParams));
        byte[] bytes = util.encodeTransactionProto(txParams);
        return bytes;
    }

    public boolean isPending() {
        return this.status.equals(TxStatus.Pending);
    }

    public boolean isInitialised() {
        return this.status.equals(TxStatus.Initialised);
    }

    public boolean isConfirmed() {
        return this.status.equals(TxStatus.Confirmed);
    }

    public boolean isRejected() {
        return this.status.equals(TxStatus.Rejected);
    }

    public boolean trackTx(String txHash) {
        Transaction respose;
        try {
            respose = this.provider.getTransaction(txHash);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        this.setID(respose.getID());
        this.setReceipt(respose.getReceipt());
        if (respose.getReceipt() != null && respose.getReceipt().isSuccess()) {
            this.setStatus(TxStatus.Confirmed);
        } else {
            this.setStatus(TxStatus.Rejected);
        }
        return true;
    }


}
