package com.firestack.laksaj.blockchain;

public abstract class TransactionReceipt {
    protected boolean success;
    protected String cumulativeGas;
    protected EventLogEntry[] eventLogs;
}
