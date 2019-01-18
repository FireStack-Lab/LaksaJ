package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class EventParam {
    private String vname;
    private String type;
    private String value;
}
