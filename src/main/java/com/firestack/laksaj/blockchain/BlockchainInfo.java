package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class BlockchainInfo {
    private int NumPeers;
    private String NumTxBlocks;
    private String NumDSBlocks;
    private String NumTransactions;
    private String TransactionRate;
    private int TxBlockRate;
    private int DSBlockRate;
    private String CurrentMiniEpoch;
    private String CurrentDSEpoch;
    private String NumTxnsDSEpoch;
    private int NumTxnsTxEpoch;
    private ShardingStructure shardingStructure;
}
