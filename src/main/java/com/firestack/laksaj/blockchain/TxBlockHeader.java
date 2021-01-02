package com.firestack.laksaj.blockchain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TxBlockHeader {
    private String BlockNum;
    private String DSBlockNum;
    private String GasLimit;
    private String GasUsed;
    private String MbInfoHash;
    private String MinerPubKey;
    private int NumMicroBlocks;
    private int NumTxns;
    private String PrevBlockHash;
    private String Rewards;
    private String StateDeltaHash;
    private String StateRootHash;
    private String Timestamp;
    private int Type;
    private int Version;
}
