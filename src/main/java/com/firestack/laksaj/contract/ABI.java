package com.firestack.laksaj.contract;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ABI {
    private String name;
    private Field[] fields;
    private Field[] params;
    private Transition[] transitions;
}
