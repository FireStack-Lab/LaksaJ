package com.firestack.laksaj.contract;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Field {
    private String name;
    private String type;
}
