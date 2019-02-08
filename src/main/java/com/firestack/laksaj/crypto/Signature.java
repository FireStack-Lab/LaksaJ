package com.firestack.laksaj.crypto;

import lombok.Data;
import lombok.Builder;

import java.math.BigInteger;

@Data
@Builder
public class Signature {
    private BigInteger r;
    private BigInteger s;

    @Override
    public String toString() {
        String rs = r.toString(16);
        String ss = s.toString(16);
        return rs + ss;
    }

    public boolean isNil() {
        return this.r.equals(BigInteger.ZERO) && this.s.equals(BigInteger.ZERO);
    }
}
