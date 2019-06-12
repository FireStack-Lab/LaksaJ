package com.firestack.laksaj.exception;

import lombok.Data;

@Data
public class ZilliqaAPIException extends Exception {
    private String message;

    public ZilliqaAPIException(String message) {
        this.message = message;
    }
}
