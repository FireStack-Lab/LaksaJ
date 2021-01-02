package com.firestack.laksaj.jsonrpc;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Req {
    private String id;
    private String jsonrpc;
    private String method;
    private Object[] params;
}