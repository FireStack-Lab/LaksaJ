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
        String rs = r.toString(16);
        String ss = s.toString(16);
        System.out.println("r is:" + rs);
        System.out.println("s is:" + ss);
        return rs + ss;
    }
}
