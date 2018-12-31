package com.firestack.laksaj.blockchain;


import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class TransactionReceipt {
    private boolean success;
    private String cumulativeGas;
    private EventLogEntry[] eventLogs;
}
