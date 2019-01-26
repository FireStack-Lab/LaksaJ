package com.firestack.example;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Wallet wallet = new Wallet();
        String ptivateKey = "e19d05c5452598e24caad4a0d85a49146f7be089515c905ae6a19e8a578a6930";
        // Populate the wallet with an account
        String address = wallet.addByPrivateKey(ptivateKey);
        System.out.println("address is: " + address);

        HttpProvider provider = new HttpProvider("https://api.zilliqa.com");
        //get balance
        HttpProvider.BalanceResult balanceResult = provider.getBalance(address);
        System.out.println("balance is: " + balanceResult.getBalance());

    }
}
