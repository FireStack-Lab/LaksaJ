package com.firestack.laksaj.blockchain;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockList {
    private BlockShort[] data;
    private int maxPages;
}
