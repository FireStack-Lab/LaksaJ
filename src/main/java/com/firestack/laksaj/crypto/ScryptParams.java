package com.firestack.laksaj.crypto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScryptParams implements KDFParams {
    private String salt;
    private int dkLen;
    private int n;
    private int r;
    private int p;
}
