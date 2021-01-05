package com.firestack.laksaj.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionStatus {

    @Data
    @Builder
    public static class InternalId {
        private String $oid;
    }

    private String ID;
    private InternalId _id;
    private String amount;
    private String data;
    private String epochInserted;
    private String epochUpdated;
    private String gasLimit;
    private String gasPrice;
    private String lastModified;
    private Integer modificationState;
    private String nonce;
    private String senderAddr;
    private String signature;
    private Integer status;
    private Boolean success;
    private String toAddr;
    private String version;

    private String info;
}


