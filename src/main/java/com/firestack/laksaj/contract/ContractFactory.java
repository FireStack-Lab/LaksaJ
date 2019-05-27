package com.firestack.laksaj.contract;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.crypto.KeyTools;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.utils.ByteUtil;
import com.firestack.laksaj.utils.Validation;
import lombok.Builder;
import lombok.Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Data
@Builder
public class ContractFactory {
    private Wallet signer;
    private HttpProvider provider;

    public static String getAddressForContract(Transaction tx) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String senderAddress = KeyTools.getAddressFromPublicKey(tx.getSenderPubKey());
        messageDigest.update(ByteUtil.hexStringToByteArray(senderAddress));

        int nonce = 0;
        if (null != tx.getNonce() && !tx.getNonce().isEmpty()) {
            nonce = Integer.parseInt(tx.getNonce());
            nonce--;
        }
        String hexNonce = Validation.intToHex(nonce, 16);

        messageDigest.update(ByteUtil.hexStringToByteArray(hexNonce));

        byte[] bytes = messageDigest.digest();

        return ByteUtil.byteArrayToHexString(bytes).substring(24);
    }

    public Contract newContract(String code, Value[] init, String abi) throws Exception {
        return new Contract(this, code, abi, null, init, null);
    }

    public Contract atContract(String address, String code, Value[] init, String abi) throws Exception {
        return new Contract(this, code, abi, address, init, null);
    }


}
