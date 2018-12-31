package com.firestack.laksaj.crypto;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class KeystoreV3 {
    @Data
    public static class Crypto {
        private String cipher;
        private CipherParams cipherParams;
        private KDFParams kdfParams;
        private String mac;
    }

    @Data
    public static class CipherParams {
        private String string;
    }

    private String address;
    private Crypto crypto;
    private String id;
    private int version = 3;
}
