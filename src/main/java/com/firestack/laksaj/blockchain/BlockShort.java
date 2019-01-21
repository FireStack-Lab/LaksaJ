package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class BlockShort {
    private int BlockNum;
    private String Hash;
}
