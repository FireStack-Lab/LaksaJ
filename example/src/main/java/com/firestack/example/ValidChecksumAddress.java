package com.firestack.example;

import com.firestack.laksaj.utils.Validation;

public class ValidChecksumAddress {
    public static void main(String[] args) {
        String checksumAddress = "0x4BAF5faDA8e5Db92C3d3242618c5B47133AE003C";
        boolean isChecksumAddress = Validation.isValidChecksumAddress(checksumAddress);
        System.out.println("is checksum address: " + isChecksumAddress);
    }
}
