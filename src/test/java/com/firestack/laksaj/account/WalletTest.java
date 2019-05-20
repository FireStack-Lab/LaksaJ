package com.firestack.laksaj.account;

import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TransactionFactory;
import org.junit.Test;

import java.io.IOException;

import static com.firestack.laksaj.account.Wallet.pack;

public class WalletTest {
    @Test
    public void sendTransactionTest() throws Exception {
        Wallet wallet = new Wallet();
        String ptivateKey = "e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930";
        // Populate the wallet with an account
        String address = wallet.addByPrivateKey(ptivateKey);
        System.out.println("address is: " + address);
        wallet.addByPrivateKey(ptivateKey);

        HttpProvider provider = new HttpProvider("https://dev-api.zilliqa.com/");
        wallet.setProvider(provider);
        //get balance
        HttpProvider.BalanceResult balanceResult = provider.getBalance(address).getResult();
        System.out.println("balance is: " + balanceResult.getBalance());

        //construct non-contract transaction
        Transaction transaction = Transaction.builder()
                .version(String.valueOf(pack(333, 2)))
//                .toAddr("24A4zoHhcP4PGia5e5aCnEbq4fQw")
//                .toAddr("0x4baf5fada8e5db92c3d3242618c5b47133ae003c".toLowerCase())
                .toAddr("zil1n0lvw9dxh4jcljmzkruvexl69t08zs62ds9ats")
                .senderPubKey("0246E7178DC8253201101E18FD6F6EB9972451D121FC57AA2A06DD5C111E58DC6A")
                .amount("10000000")
                .gasPrice("1000000000")
                .gasLimit("1")
                .code("")
                .data("")
                .provider(new HttpProvider("https://dev-api.zilliqa.com/"))
                .build();

        //sign transaction
        transaction = wallet.sign(transaction);
        System.out.println("signature is: " + transaction.getSignature());

        //broadcast transaction
        HttpProvider.CreateTxResult result = TransactionFactory.createTransaction(transaction);
        System.out.println(result.getTranID());
        transaction.confirm(result.getTranID(),100,10);

    }
}
