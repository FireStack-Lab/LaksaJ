package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class TransactionList {
    private int number;
    private String[] transactionHashes;
}
