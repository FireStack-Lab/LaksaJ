package com.firestack.laksaj.transaction;


import com.firestack.laksaj.blockchain.TransactionReceipt;
import com.firestack.laksaj.jsonrpc.Provider;
import lombok.Data;
import lombok.experimental.Builder;

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

    public static Transaction buildTransaction(TxParams params, Provider provider, TxStatus status) {
        return Transaction.builder()
                .ID(params.getID())
                .version(params.getVersion())
                .nonce(params.getNonce())
                .amount(params.getAmount())
                .gasPrice(params.getGasPrice())
                .gasLimit(params.getGasLimit())
                .signature(params.getSignature())
                .receipt(params.getReceipt())
                .senderPubKey(params.getSenderPubKey())
                .toAddr(params.getToAddr())
                .code(params.getCode().orElse(""))
                .data(params.getData().orElse(""))
                .provider(provider)
                .status(status)
                .build();
    }

}
