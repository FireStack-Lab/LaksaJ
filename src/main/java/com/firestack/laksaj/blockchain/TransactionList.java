package com.firestack.laksaj.blockchain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionList {
    private int number;
    private String[] TxnHashes;
}
