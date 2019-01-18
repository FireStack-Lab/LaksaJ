package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class TransactionList {
    private int number;
    private String[] TxnHashes;
}
