package com.firestack.laksaj.blockchain;


import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class TransactionReceipt {
    private boolean success;
    private String cumulative_gas;
    private String epoch_num;
}
