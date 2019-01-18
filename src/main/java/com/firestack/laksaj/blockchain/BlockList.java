package com.firestack.laksaj.blockchain;


import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class BlockList {
    private BlockShort[] data;
    private int maxPages;
}
