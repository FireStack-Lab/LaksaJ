package com.firestack.laksaj.contract;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeployParams {
    private String ID;
    private String version;
    private String nonce;
    private String gasPrice;
    private String gasLimit;
    private String senderPubKey;
}
