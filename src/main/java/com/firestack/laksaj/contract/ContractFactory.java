package com.firestack.laksaj.contract;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.utils.ByteUtil;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import lombok.Data;

@Data
public class ContractFactory {
    private Wallet signer;
    private HttpProvider provider;

    //todo need test
    public static String getAddressForContract(Transaction tx) {
        HashFunction function = Hashing.sha256();
        Hasher hasher = function.newHasher();
        hasher.putBytes(ByteUtil.hexStringToByteArray(tx.getSenderPubKey()));
        int nonce = Integer.parseInt(tx.getNonce());
        hasher.putBytes(ByteUtil.hexStringToByteArray(Integer.toHexString(nonce)));
        byte[] bytes = hasher.hash().asBytes();
        return ByteUtil.byteArrayToHexString(bytes).substring(24);
    }
}
