package com.firestack.laksaj.contract;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Value {
    private String vname;
    private String type;
    private String value;
}

