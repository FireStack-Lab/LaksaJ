package com.firestack.example;

import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.TransactionStatus;

import java.io.IOException;

public class GetTransactionStatus {
    public static void main(String[] args) throws IOException {
        HttpProvider provider = new HttpProvider("https://api.zilliqa.com");
        TransactionStatus status = provider.getTransactionStatus("347a3d1f7393843b547b2d341a69b092473a26cb531eb8aabaffe1c790e1c70e").getResult();
        System.out.println(status);
    }
}
