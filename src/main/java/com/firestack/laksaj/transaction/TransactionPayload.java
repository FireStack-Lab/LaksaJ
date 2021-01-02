package com.firestack.laksaj.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionPayload {
    private int version;
    private int nonce;
    private String toAddr;
    private String amount;
    private String pubKey;
    private String gasPrice;
    private String gasLimit;
    private String code;
    private String data;
    private String signature;
}
