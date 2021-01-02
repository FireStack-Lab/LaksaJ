package com.firestack.laksaj.utils;

import com.firestack.laksaj.transaction.TxParams;
import org.junit.Test;

public class TransactionUtilTest {
    @Test
    public void encodeTransactionProto() {
        TxParams txParams = TxParams.builder()
                .version("0")
                .nonce("0")
                .toAddr("2E3C9B415B19AE4035503A06192A0FAD76E04243")
                .senderPubKey("0246e7178dc8253201101e18fd6f6eb9972451d121fc57aa2a06dd5c111e58dc6a")
                .amount("10000")
                .gasPrice("100")
                .gasLimit("1000")
                .code("")
                .data("")
                .build();
        TransactionUtil util = new TransactionUtil();
        byte[] bytes = util.encodeTransactionProto(txParams);
        System.out.println(ByteUtil.byteArrayToHexString(bytes));
    }
}
