package com.firestack.laksaj.account;

import com.firestack.laksaj.jsonrpc.Provider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TransactionFactory;
import org.junit.Test;

import java.io.IOException;

public class WalletTest {
    @Test
    public void sign() throws IOException {
        Wallet wallet = new Wallet();
        String address = wallet.addByPrivateKey("e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930".toUpperCase());
        Transaction transaction = Transaction.builder()
                .version(String.valueOf(pack(62,8)))
                .toAddr("0x4baf5fada8e5db92c3d3242618c5b47133ae003c".toLowerCase())
                .senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a".toLowerCase())
                .amount("1000000")
                .gasPrice("1000000")
                .gasLimit("1")
                .code("")
                .data("")
                .provider(new Provider("https://api.zilliqa.com/"))
                .build();
        transaction = wallet.sign(transaction);
        System.out.println("signature is: " + transaction.getSignature());
        Provider.CreateTxResult result = TransactionFactory.sendTransaction(transaction);
        System.out.println(result);
    }

    int pack(int a, int b) {
        return (a << 16) + b;
    }
}


//"4c6fdae82ea4a928e5996f04844d87e2801e1461c534f3dfd7029440e7ce8d23e022c50d3137ad7db3b84e11efc345e8febe2fc1834f5049bc9d832f39608009"