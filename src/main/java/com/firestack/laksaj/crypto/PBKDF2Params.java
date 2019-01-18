package com.firestack.laksaj.crypto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class PBKDF2Params implements KDFParams {
    private String salt;
    private int dkLen;
    private int count;
}
