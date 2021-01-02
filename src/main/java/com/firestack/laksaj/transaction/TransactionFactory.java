package com.firestack.laksaj.transaction;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.SECONDS;


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

    public static HttpProvider.CreateTxResult createTransaction(Transaction signedTx) throws Exception {
        return signedTx.getProvider().createTransaction(signedTx.toTransactionPayload()).getResult();
    }

    public static List<TransactionPayload> toPayloads(List<Transaction> transactions) throws Exception {
        List<TransactionPayload> payloads = new ArrayList<>();
        for (int i = 0; i < transactions.size(); i++) {
            payloads.add(transactions.get(i).toTransactionPayload());
        }
        return payloads;
    }

    public static List<Transaction> batchConfirm(List<Transaction> transactions, int maxAttempts, int interval) throws InterruptedException {
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            // if the status is rejected already, we don't track it at all
            if (TxStatus.Rejected != transaction.getStatus()) {
                transaction.setStatus(TxStatus.Pending);
            }

        }

        for (int i = 0; i < maxAttempts; i++) {
            Thread.sleep(Duration.of(interval, SECONDS).toMillis());
            for (int j = 0; j < transactions.size(); j++) {
                Transaction transaction = transactions.get(j);
                // if transaction status is pending, track it, otherwise just ignore it
                if (TxStatus.Pending == transaction.getStatus()) {
                    if (transaction.trackTx(transaction.getID())) {
                        transaction.setStatus(TxStatus.Confirmed);
                    }
                }
            }
        }

        for (int j = 0; j < transactions.size(); j++) {
            Transaction transaction = transactions.get(j);
            // if transaction status still pending, set it to reject
            if (TxStatus.Pending == transaction.getStatus()) {
                transaction.setStatus(TxStatus.Rejected);
                transaction.setInfo("cannot track after time limit");
            }
        }

        return transactions;
    }


}
