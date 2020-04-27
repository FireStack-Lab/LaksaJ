package com.firestack.laksaj.crypto;

import lombok.Builder;
import lombok.Data;
import org.bouncycastle.util.encoders.Hex;

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

        while (rHex.length() > 64 && rHex.charAt(0) == '0') {
            rHex = rHex.substring(1);
        }

        String sHex = Hex.toHexString(s.toByteArray());
        while (sHex.length() < 64) {
            sHex = "0" + sHex;
        }

        while (sHex.length() > 64 && sHex.charAt(0) == '0') {
            sHex = sHex.substring(1);
        }

        return rHex + sHex;
    }

    public boolean isNil() {
        return this.r.equals(BigInteger.ZERO) && this.s.equals(BigInteger.ZERO);
    }
}
