package com.firestack.laksaj.contract;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class CallParams {
    private String ID;
    private String version;
    private String nonce;
    private String amount;
    private String gasPrice;
    private String gasLimit;
    private String senderPubKey;
}
