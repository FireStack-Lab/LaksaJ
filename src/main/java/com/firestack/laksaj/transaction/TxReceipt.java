package com.firestack.laksaj.transaction;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class TxReceipt {
    private boolean success;
    private int cumulativeGas;
}
