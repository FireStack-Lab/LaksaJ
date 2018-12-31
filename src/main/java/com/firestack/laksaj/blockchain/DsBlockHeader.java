package com.firestack.laksaj.blockchain;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class DsBlockHeader {
    private String blockNumber;
    private int difficulty;
    private int diffcultyDS;
    private int gasPrice;
    private String leaderPublicKey;
    private String[] powWinners;
    private String previousHash;
    private String timestamp;
}
