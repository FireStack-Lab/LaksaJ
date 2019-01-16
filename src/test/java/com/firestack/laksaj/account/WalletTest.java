package com.firestack.laksaj.account;

import com.firestack.laksaj.transaction.Transaction;
import org.junit.Test;

public class WalletTest {
    @Test
    public void sign() {
        Wallet wallet = new Wallet();
        String address = wallet.addByPrivateKey("e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930");
        System.out.println("address is: " + address);
        Transaction transaction = Transaction.builder()
                .version("4063233")
                .toAddr("2E3c9B415b19AE4035503a06192A0fAd76E04243")
                .nonce("24")
                .senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a")
                .amount("24000000000000")
                .gasPrice("1000000000")
                .gasLimit("1")
                .code("")
                .data("")
                .build();
        transaction = wallet.sign(transaction);
        System.out.println("signature is: " + transaction.getSignature());

    }
}


//"4c6fdae82ea4a928e5996f04844d87e2801e1461c534f3dfd7029440e7ce8d23e022c50d3137ad7db3b84e11efc345e8febe2fc1834f5049bc9d832f39608009"