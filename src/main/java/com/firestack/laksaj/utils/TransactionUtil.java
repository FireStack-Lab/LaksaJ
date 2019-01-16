package com.firestack.laksaj.utils;

import com.firestack.laksaj.proto.Message;
import com.firestack.laksaj.transaction.TxParams;
import com.google.common.base.Strings;
import com.google.protobuf.ByteString;
import org.bouncycastle.util.BigIntegers;

import java.math.BigInteger;

public class TransactionUtil {

    public byte[] encodeTransactionProto(TxParams txParams) {
        BigInteger amount = new BigInteger(txParams.getAmount());
        BigInteger gasPrice = new BigInteger(txParams.getGasPrice());
        return Message.ProtoTransactionCoreInfo.newBuilder()
                .setVersion(Integer.valueOf(txParams.getVersion()))
                .setNonce(Strings.isNullOrEmpty(txParams.getNonce()) ? 0 : Long.valueOf(txParams.getNonce()))
                .setToaddr(ByteString.copyFrom(ByteUtil.hexStringToByteArray(txParams.getToAddr().toLowerCase())))
                .setSenderpubkey(Message.ByteArray.newBuilder().setData(ByteString.copyFrom(ByteUtil.hexStringToByteArray(txParams.getSenderPubKey()))).build())
                .setAmount(Message.ByteArray.newBuilder().setData(ByteString.copyFrom(BigIntegers.asUnsignedByteArray(16, amount))).build())
                .setGasprice(Message.ByteArray.newBuilder().setData(ByteString.copyFrom(BigIntegers.asUnsignedByteArray(16, gasPrice))).build())
                .setGaslimit(Long.valueOf(txParams.getGasLimit()))
                .setCode(ByteString.copyFrom(txParams.getCode().orElse("").getBytes()))
                .setData(ByteString.copyFrom(txParams.getData().orElse("").getBytes()))
                .build().toByteArray();
    }

}
