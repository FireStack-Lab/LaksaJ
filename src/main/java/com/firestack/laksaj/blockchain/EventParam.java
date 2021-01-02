package com.firestack.laksaj.blockchain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventParam {
    private String vname;
    private String type;
    private String value;
}
