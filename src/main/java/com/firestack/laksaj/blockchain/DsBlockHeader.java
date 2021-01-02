package com.firestack.laksaj.blockchain;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DsBlockHeader {
    @SerializedName("BlockNum")
    private String blockNumber;
    @SerializedName("Difficulty")
    private int difficulty;
    @SerializedName("DifficultyDS")
    private int diffcultyDS;
    @SerializedName("GasPrice")
    private int gasPrice;
    @SerializedName("LeaderPubKey")
    private String leaderPublicKey;
    @SerializedName("PoWWinners")
    private String[] powWinners;
    @SerializedName("PrevHash")
    private String previousHash;
    @SerializedName("Timestamp")
    private String timestamp;
}
