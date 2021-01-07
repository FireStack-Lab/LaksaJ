package com.firestack.laksaj.crypto;

import java.security.SecureRandom;

public class CustomSecureRandom extends SecureRandom {

    public CustomSecureRandom() {
        super(new LinuxSecureRandom(), new LinuxSecureRandomProvider());
    }
}
