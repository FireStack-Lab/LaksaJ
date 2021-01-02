package com.firestack.laksaj.jsonrpc;

import lombok.Data;

@Data
public class Rep<R> {
    private String id;
    private String jsonrpc;
    private R result;
    private HttpProvider.CreateTxError error;
    private String err;
}
