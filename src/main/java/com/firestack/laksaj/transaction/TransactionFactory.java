package com.firestack.laksaj.transaction;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class TransactionFactory {
    public static Transaction buildTransaction(TxParams params, HttpProvider provider, TxStatus status) {
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
                .code(params.getCode())
                .data(params.getData())
                .provider(provider)
                .status(status)
                .build();
    }

    public static HttpProvider.CreateTxResult createTransaction(Transaction signedTx) throws IOException {
        return signedTx.getProvider().createTransaction(signedTx.toTransactionPayload()).getResult();
    }
}
