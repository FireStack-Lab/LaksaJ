package com.firestack.laksaj.crypto;

import java.security.Provider;

public class LinuxSecureRandomProvider extends Provider {

    public LinuxSecureRandomProvider() {
        super("LinuxSecureRandom", 1.0, "A Linux specific random number provider that uses /dev/urandom");
        put("SecureRandom.LinuxSecureRandom", LinuxSecureRandom.class.getName());
    }
}
