package com.firestack.laksaj.jsonrpc;

import lombok.Data;
import lombok.experimental.Builder;

@Data
@Builder
public class Req {
    private String id;
    private String jsonrpc;
    private String method;
    private Object[] params;
}