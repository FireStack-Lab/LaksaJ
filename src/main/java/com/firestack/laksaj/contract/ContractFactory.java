package com.firestack.laksaj.contract;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.utils.ByteUtil;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractFactory {
    private Wallet signer;
    private HttpProvider provider;

    //todo need test
    public static String getAddressForContract(Transaction tx) {
        HashFunction function = Hashing.sha256();
        Hasher hasher = function.newHasher();
        hasher.putBytes(ByteUtil.hexStringToByteArray(tx.getSenderPubKey()));
        int nonce = 0;
        if (tx.getNonce() != null && !tx.getNonce().isEmpty()) {
            nonce = Integer.parseInt(tx.getNonce());
            nonce = nonce - 1;
        }

        String hexNonce = Integer.toHexString(nonce);
        if (hexNonce.length() % 2 != 0) {
            hexNonce = "0" + hexNonce;
        }
        System.out.println("hex string is: " + hexNonce);
        hasher.putBytes(ByteUtil.hexStringToByteArray(hexNonce));
        byte[] bytes = hasher.hash().asBytes();
        return ByteUtil.byteArrayToHexString(bytes).substring(24);
    }

    public Contract newContract(String code, Value[] init, String abi) {
        return new Contract(this, code, abi, null, init, null);
    }
}
