package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class BlockShort {
    private int blockNumber;
    private String hash;
}
