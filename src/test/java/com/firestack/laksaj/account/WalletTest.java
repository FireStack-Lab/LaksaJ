package com.firestack.laksaj.account;

import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TransactionFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.firestack.laksaj.account.Wallet.pack;

public class WalletTest {
    @Test
    public void sendTransactionTest() throws Exception {
        Wallet wallet = new Wallet();
        String ptivateKey = "e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930";
        // Populate the wallet with an account
        String address = wallet.addByPrivateKey(ptivateKey);
        wallet.addByPrivateKey(ptivateKey);

        HttpProvider provider = new HttpProvider("https://dev-api.zilliqa.com/");
        wallet.setProvider(provider);
        //get balance
        HttpProvider.BalanceResult balanceResult = provider.getBalance(address).getResult();

        //construct non-contract transaction
        Transaction transaction = Transaction.builder()
                .version(String.valueOf(pack(333, 1)))
//                .toAddr("24A4zoHhcP4PGia5e5aCnEbq4fQw")
//                .toAddr("0x4baf5fada8e5db92c3d3242618c5b47133ae003c".toLowerCase())
//                .toAddr("4BAF5faDA8e5Db92C3d3242618c5B47133AE003C")
                .toAddr("zil16jrfrs8vfdtc74yzhyy83je4s4c5sqrcasjlc4")
                .senderPubKey("0246E7178DC8253201101E18FD6F6EB9972451D121FC57AA2A06DD5C111E58DC6A")
                .amount("10000000")
                .gasPrice("2000000000")
                .gasLimit("1")
                .code("")
                .data("")
                .provider(new HttpProvider("https://dev-api.zilliqa.com/"))
                .build();

        //sign transaction
        transaction = wallet.sign(transaction);

        //broadcast transaction
        HttpProvider.CreateTxResult result = TransactionFactory.createTransaction(transaction);
        transaction.confirm(result.getTranID(), 100, 10);
    }

    @Test
    public void sendTransactionsTest() throws Exception {
        Wallet wallet = new Wallet();
        String ptivateKey = "e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930";
        // Populate the wallet with an account
        String address = wallet.addByPrivateKey(ptivateKey);
        wallet.addByPrivateKey(ptivateKey);

        HttpProvider provider = new HttpProvider("https://dev-api.zilliqa.com/");
        wallet.setProvider(provider);
        //get balance
        HttpProvider.BalanceResult balanceResult = provider.getBalance(address).getResult();
        Integer nonce = Integer.parseInt(balanceResult.getNonce());

        //construct non-contract transactions
        List<Transaction> transactions = new ArrayList<>();
        Transaction tx1 = Transaction.builder()
                .version(String.valueOf(pack(333, 1)))
                .toAddr("zil16jrfrs8vfdtc74yzhyy83je4s4c5sqrcasjlc4")
                .senderPubKey("0246E7178DC8253201101E18FD6F6EB9972451D121FC57AA2A06DD5C111E58DC6A")
                .amount("10000000")
                .nonce(Integer.toString(nonce + 1))
                .gasPrice("2000000000")
                .gasLimit("1")
                .code("")
                .data("")
                .provider(provider)
                .build();

        Transaction tx2 = Transaction.builder()
                .version(String.valueOf(pack(333, 1)))
                .toAddr("zil16jrfrs8vfdtc74yzhyy83je4s4c5sqrcasjlc4")
                .senderPubKey("0246E7178DC8253201101E18FD6F6EB9972451D121FC57AA2A06DD5C111E58DC6A")
                .amount("10000000")
                .gasPrice("2000000000")
                .gasLimit("1")
                .nonce(Integer.toString(nonce + 2))
                .code("")
                .data("")
                .provider(provider)
                .build();

        Transaction tx3 = Transaction.builder()
                .version(String.valueOf(pack(333, 1)))
                .toAddr("zil1n0lvw9dxh4jcljmzkruvexl69t08zs62ds9ats")
                .senderPubKey("0246E7178DC8253201101E18FD6F6EB9972451D121FC57AA2A06DD5C111E58DC6A")
                .amount("10000000")
                .gasPrice("1000000000")
                .gasLimit("1")
                .nonce(Integer.toString(nonce + 2))
                .code("")
                .data("")
                .provider(provider)
                .build();

        transactions.add(tx1);
        transactions.add(tx2);
        transactions.add(tx3);


        wallet.batchSign(transactions);
        provider.createTransactions(transactions);
        TransactionFactory.batchConfirm(transactions, 9, 10);

        // do some post check, e.g check errors
        for (int i = 0; i < transactions.size(); i++) {
            // we expected transaction 3 is failed because of gas fee setting
            // (whose to address is zil1n0lvw9dxh4jcljmzkruvexl69t08zs62ds9ats)
            if (transactions.get(i).getToAddr().equals("0x9BFEC715a6bD658fCb62B0f8cc9BFa2ADE71434A")) {
                System.out.println(transactions.get(i).getInfo());
            }
        }

    }
}
