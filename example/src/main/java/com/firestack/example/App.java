package com.firestack.example;

import com.firestack.laksaj.blockchain.DsBlock;
import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
    }
}
