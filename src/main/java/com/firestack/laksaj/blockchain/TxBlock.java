package com.firestack.laksaj.blockchain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxBlock {

    @Data
    @Builder
    public static class MicroBlockInfo {
        private String MicroBlockHash;
        private int MicroBlockShardId;
        private String MicroBlockTxnRootHash;
    }

    @Data
    @Builder
    public static class Body {
        private String BlockHash;
        private String HeaderSign;
        private MicroBlockInfo[] MicroBlockInfos;
    }

    private Body body;
    private TxBlockHeader header;
}