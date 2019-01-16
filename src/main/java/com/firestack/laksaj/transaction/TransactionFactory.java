package com.firestack.laksaj.transaction;

import com.firestack.laksaj.jsonrpc.Provider;

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
                .code(params.getCode().orElse(""))
                .data(params.getData().orElse(""))
                .provider(provider)
                .status(status)
                .build();
    }
}
