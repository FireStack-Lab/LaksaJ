package com.firestack.laksaj.transaction;

import lombok.Data;
import lombok.experimental.Builder;

import java.math.BigInteger;
import java.util.Optional;

@Data
@Builder
public class TxParams {
    private int version;
    private String toAddr;
    private BigInteger amount;
    private BigInteger gasPrice;
    private long gasLimit;
    private Optional<String> code;
    private Optional<String> data;
    private Optional<TxReceipt> receipt;
    private Optional<Integer> nonce;
    private Optional<String> pubKey;
    private Optional<String> signature;

}
