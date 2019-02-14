package com.firestack.laksaj.crypto;

import lombok.Data;
import lombok.Builder;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

@Data
@Builder
public class Signature {
    private BigInteger r;
    private BigInteger s;

    @Override
    public String toString() {
        String rHex = Hex.toHexString(r.toByteArray());
        while (rHex.length() < 64) {
            rHex = "0" + rHex;
        }

        String sHex = Hex.toHexString(s.toByteArray());
        while (sHex.length() < 64) {
            sHex = "0" + sHex;
        }
        return rHex + sHex;
    }

    public boolean isNil() {
        return this.r.equals(BigInteger.ZERO) && this.s.equals(BigInteger.ZERO);
    }
}
