package com.firestack.laksaj.jsonrpc;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class Req {
    private String id;
    private String jsonrpc;
    private String method;
    private Object[] params;
}