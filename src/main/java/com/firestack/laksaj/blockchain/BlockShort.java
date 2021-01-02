package com.firestack.laksaj.blockchain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockShort {
    private int BlockNum;
    private String Hash;
}
