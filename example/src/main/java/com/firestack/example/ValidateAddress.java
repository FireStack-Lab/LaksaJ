package com.firestack.example;

import com.firestack.laksaj.utils.Validation;

public class ValidateAddress {
    public static void main(String[] args) {
        String address = "2624B9EA4B1CD740630F6BF2FEA82AAC0067070B";
        boolean isAddress = Validation.isAddress(address);
        System.out.println("is address: " + isAddress);
    }
}
