package com.firestack.laksaj.blockchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DsBlock {
    private DsBlockHeader header;
    private String signature;
}
