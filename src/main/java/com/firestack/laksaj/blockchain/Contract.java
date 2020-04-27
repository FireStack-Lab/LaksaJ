package com.firestack.laksaj.blockchain;

import lombok.Data;

@Data
public class Contract {
    private String address;
    private State[] state;

    @Data
    public static class State {
        private String type;
        private Object value;
        private String vname;
    }
}
