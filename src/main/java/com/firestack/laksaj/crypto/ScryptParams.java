package com.firestack.laksaj.crypto;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class ScryptParams implements KDFParams {
    private String salt;
    private int dkLen;
    private int n;
    private int r;
    private int p;
}
