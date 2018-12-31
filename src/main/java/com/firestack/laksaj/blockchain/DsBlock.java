package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class DsBlock {
    private DsBlockHeader header;
    private String signature;
}
