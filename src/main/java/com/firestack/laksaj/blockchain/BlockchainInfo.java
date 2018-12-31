package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class BlockchainInfo {
    private int numPeers;
    private String numTxBlocks;
    private String numDSBlocks;
    private String numTransactions;
    private String transactionRate;
    private int txBlockRate;
    private int DSBlockRate;
    private String currentMiniEpoch;
    private String currentDSEpoch;
    private String numTxnsDSEpoch;
    private int numTxnsTxEpoch;
    private ShardingStructure shardingStructure;
}
