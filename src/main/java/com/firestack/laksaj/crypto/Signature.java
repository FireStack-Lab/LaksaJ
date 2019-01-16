package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;
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
        return ByteUtil.byteArrayToHexString(r.toByteArray()) + ByteUtil.byteArrayToHexString(s.toByteArray());
    }
}
