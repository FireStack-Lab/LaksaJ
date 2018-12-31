package com.firestack.laksaj.blockchain;


import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class BlockList {
    private BlockShort[] data;
    private int maxPages;
}
