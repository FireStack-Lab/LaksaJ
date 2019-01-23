package com.firestack.laksaj.contract;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.crypto.KeyTools;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.utils.ByteUtil;
import com.firestack.laksaj.utils.HashUtil;
import com.firestack.laksaj.utils.Validation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContractFactory {
    private Wallet signer;
    private HttpProvider provider;

    //todo need test,do not use this!!!
    public static String getAddressForContract(Transaction tx) {
        String senderAddress = KeyTools.getAddressFromPublicKey(tx.getSenderPubKey());
        System.out.println("sender address is: " + senderAddress);
        byte[] address = ByteUtil.hexStringToByteArray("0x" + senderAddress);

        int nonce = 0;
        if (null != tx.getNonce() && !tx.getNonce().isEmpty()) {
            nonce = Integer.parseInt(tx.getNonce());
            nonce--;
        }
        String hexNonce = Validation.intToHex(nonce, 16);

        System.out.println("hex nonce is: " + hexNonce);
        byte[] nonceBytes = ByteUtil.hexStringToByteArray(hexNonce);

        byte[] bytes = new byte[address.length + nonceBytes.length];

        for (int i = 0; i < address.length; i++) {
            bytes[i] = address[i];
        }

        for (int i = 0; i < nonceBytes.length; i++) {
            bytes[address.length + i] = nonceBytes[i];
        }

        byte[] result = HashUtil.sha256(bytes);
        return ByteUtil.byteArrayToHexString(result).substring(24);
    }

    public Contract newContract(String code, Value[] init, String abi) {
        return new Contract(this, code, abi, null, init, null);
    }

    public Contract atContract(String address, String code, Value[] init, String abi) {
        return new Contract(this, code, abi, address, init, null);
    }


}
