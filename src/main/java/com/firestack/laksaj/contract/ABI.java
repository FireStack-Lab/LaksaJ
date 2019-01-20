package com.firestack.laksaj.contract;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class ABI {
    private String name;
    private Field[] fields;
    private Field[] params;
    private Transition[] transitions;
}
