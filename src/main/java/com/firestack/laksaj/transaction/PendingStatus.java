package com.firestack.laksaj.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PendingStatus {
    private Integer code;
    private boolean confirmed;
    private boolean pending;
}
