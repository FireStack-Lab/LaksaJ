package com.firestack.laksaj.blockchain;


import com.google.gson.JsonArray;
import lombok.Data;
import lombok.Builder;

import java.util.List;

@Data
@Builder
public class TransactionReceipt {
    private boolean success;
    private String cumulative_gas;
    private String epoch_num;
    //optional JsonArray
    private List<Object> event_logs;
    private List<Object> transitions;
}
