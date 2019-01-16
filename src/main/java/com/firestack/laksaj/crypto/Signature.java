package com.firestack.laksaj.crypto;

import lombok.Data;
import lombok.experimental.Builder;

import java.math.BigInteger;

@Data
@Builder
public class Signature {
    private BigInteger r;
    private BigInteger s;

    @Override
    public String toString() {
        return r.toString(16) + r.toString(16);
    }
}
