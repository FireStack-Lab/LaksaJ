package com.firestack.laksaj.transaction;

import com.firestack.laksaj.jsonrpc.Provider;

import java.io.IOException;

public class TransactionFactory {
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
                .code(params.getCode())
                .data(params.getData())
                .provider(provider)
                .status(status)
                .build();
    }

    public static Provider.CreateTxResult sendTransaction(Transaction signedTx) throws IOException {
        return signedTx.getProvider().createTransaction(signedTx.toTransactionPayload());
    }
}
