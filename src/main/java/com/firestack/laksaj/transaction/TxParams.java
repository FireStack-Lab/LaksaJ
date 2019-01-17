package com.firestack.laksaj.transaction;

import com.firestack.laksaj.blockchain.TransactionReceipt;
import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class TxParams {
    private String ID;
    private String version;
    private String nonce;
    private String amount;
    private String gasPrice;
    private String gasLimit;
    private String signature;
    private TransactionReceipt receipt;
    private String senderPubKey;
    private String toAddr;
    private String code;
    private String data;
}
