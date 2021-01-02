package com.firestack.laksaj.transaction;


import com.firestack.laksaj.account.Account;
import com.firestack.laksaj.blockchain.TransactionReceipt;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.utils.Base58;
import com.firestack.laksaj.utils.ByteUtil;
import com.firestack.laksaj.utils.TransactionUtil;
import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

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

    private HttpProvider provider;
    private TxStatus status;
    // indicate error message returned by api while creating
    private String info;

    public TxParams toTransactionParam() throws IOException {
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
                .toAddr(this.toAddr == null ? "0000000000000000000000000000000000000000" : this.toAddr.toLowerCase())
                .code(this.code)
                .data(this.data)
                .build();
    }

    public TransactionPayload toTransactionPayload() throws Exception {
        return TransactionPayload.builder()
                .version(Integer.parseInt(this.version))
                .nonce(Integer.valueOf(this.nonce))
                .toAddr(Account.normaliseAddress(this.toAddr))
                .amount(this.amount)
                .pubKey(this.senderPubKey.toLowerCase())
                .gasPrice(this.gasPrice)
                .gasLimit(this.gasLimit)
                .code(this.code)
                .data(this.data)
                .signature(this.signature.toLowerCase())
                .build();
    }

    public void marshalToAddress() throws IOException {
        byte[] address = Base58.decode(this.getToAddr());
        this.setToAddr(ByteUtil.byteArrayToHexString(address));
    }

    public byte[] bytes() throws IOException {
        TxParams txParams = toTransactionParam();
        TransactionUtil util = new TransactionUtil();
        Gson gson = new Gson();
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

    public Transaction confirm(String txHash, int maxAttempts, int interval) throws InterruptedException {
        this.setStatus(TxStatus.Pending);
        for (int i = 0; i < maxAttempts; i++) {
            boolean tracked = this.trackTx(txHash);
            Thread.sleep(Duration.of(interval, SECONDS).toMillis());

            if (tracked) {
                this.setStatus(TxStatus.Confirmed);
                return this;
            }
        }
        this.status = TxStatus.Rejected;
        return this;
    }

    public boolean trackTx(String txHash) {
        System.out.println("tracking transaction: " + txHash);
        Transaction response;
        try {
            response = this.provider.getTransaction(txHash).getResult();
        } catch (Exception e) {
            System.out.println("transaction not confirmed yet");
            return false;
        }

        if (null == response) {
            System.out.println("transaction not confirmed yet");
            return false;
        }


        this.setID(response.getID());
        this.setReceipt(response.getReceipt());
        if (response.getReceipt() != null && response.getReceipt().isSuccess()) {
            System.out.println("Transaction confirmed!");
            this.setStatus(TxStatus.Confirmed);
        } else {
            this.setStatus(TxStatus.Rejected);
            System.out.println("Transaction rejected!");

        }
        return true;
    }


}
