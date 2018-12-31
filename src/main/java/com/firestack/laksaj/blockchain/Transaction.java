package com.firestack.laksaj.blockchain;


import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Transaction {
    private String ID;
    private String version;
    private int nonce;
    private String toAddress;
    private String amount;
    private String gasPrice;
    private String gasLimit;
    private String signature;
    private TransactionReceipt receipt;
}
