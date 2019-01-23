package com.firestack.example;

import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpProvider provider = new HttpProvider("https://api.zilliqa.com/");
        System.out.println(provider.getBlockchainInfo());
    }
}
