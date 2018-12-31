package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class TransactionBlock {

    @Data
    @Builder
    public static class Body {
        private String headSign;
        private int[] microBlockEmpty;
        private int[] microBlockHashes;
    }

    private Body body;
    private TransactionBlockHeader transactionBlockHeader;
}