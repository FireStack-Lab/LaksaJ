package com.firestack.laksaj.crypto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PBKDF2Params implements KDFParams {
    private String salt;
    private int dkLen;
    private int count;
}
