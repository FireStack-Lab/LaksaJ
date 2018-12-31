package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class TransactionBlockHeader {
    private BlockType blockType;
    private String version;
    private String gasLimit;
    private String gasUsed;
    private String rewards;
    private String previousBlockHash;
    private String blockNumber;
    private String timestamp;
    private String txnHash;
    private String stateHash;
    private int numTxns;
    private int numMicroBlocks;
    private String minerPubKey;
    private String DSBlockNum;
}
