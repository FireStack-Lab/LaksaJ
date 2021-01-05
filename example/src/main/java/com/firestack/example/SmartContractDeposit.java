package com.firestack.example;

import com.firestack.laksaj.exception.ZilliqaAPIException;
import com.firestack.laksaj.jsonrpc.HttpProvider;

import java.io.IOException;

public class SmartContractDeposit {
    public static void main(String[] args) throws IOException, InterruptedException, ZilliqaAPIException {
        HttpProvider client = new HttpProvider("https://api.zilliqa.com/");
        String lastEpoch = client.getNumTxnsTxEpoch().getResult();
        String lastStateList = client.getSmartContractState("D6606D02DFF929593312D8D0D36105E376F95AA0");

        System.out.println("last epoch is " + lastEpoch);
        System.out.println("last state:" + lastStateList);

        int n = 0;

        while (true) {
            String epoch = client.getNumTxnsTxEpoch().getResult();
            System.out.println(n + "th current epoch is: " + epoch);
            if (!lastEpoch.equals(epoch)) {
                System.out.println("epoch hash changed");
                String stateList = client.getSmartContractState("D6606D02DFF929593312D8D0D36105E376F95AA0");
                System.out.println("last state: " + lastStateList);
                System.out.println("current state: " + stateList);
                lastEpoch = epoch;
                lastStateList = stateList;
            }
            Thread.sleep(3000);
            n += 1;
        }
    }
}
