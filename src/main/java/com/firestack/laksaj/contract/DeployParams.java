package com.firestack.laksaj.contract;

import lombok.Data;
import lombok.experimental.Builder;

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
