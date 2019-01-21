package com.firestack.laksaj.blockchain;


import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ShardingStructure {
    private int[] NumPeers;
}
