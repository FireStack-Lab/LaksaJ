package com.firestack.laksaj.contract;

import com.firestack.laksaj.account.Wallet;
import com.firestack.laksaj.jsonrpc.Provider;
import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Contract {
    public static String NIL_ADDRESS = "0000000000000000000000000000000000000000";

    private Value[] init;
    private ABI abi;
    private Value[] state;
    private String address;
    private String code;
    private Wallet signer;
    private Provider provider;
    private ContractStatus contractStatus;


}
