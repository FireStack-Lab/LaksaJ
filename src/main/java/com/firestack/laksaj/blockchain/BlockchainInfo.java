package com.firestack.laksaj.blockchain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockchainInfo {
    private int NumPeers;
    private String NumTxBlocks;
    private String NumDSBlocks;
    private String NumTransactions;
    private String TransactionRate;
    private double TxBlockRate;
    private double DSBlockRate;
    private String CurrentMiniEpoch;
    private String CurrentDSEpoch;
    private String NumTxnsDSEpoch;
    private int NumTxnsTxEpoch;
    private ShardingStructure ShardingStructure;
}
