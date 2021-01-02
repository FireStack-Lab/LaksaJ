package com.firestack.laksaj.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxReceipt {
    private boolean success;
    private int cumulativeGas;
}
