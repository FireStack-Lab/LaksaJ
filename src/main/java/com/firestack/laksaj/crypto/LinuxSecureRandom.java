package com.firestack.laksaj.crypto;

import java.io.*;
import java.security.SecureRandomSpi;

public class LinuxSecureRandom extends SecureRandomSpi {
    private static final FileInputStream urandom;

    static {
        try {
            File file = new File("/dev/urandom");
            urandom = new FileInputStream(file);
            if (urandom.read() == -1) {
                throw new RuntimeException("/dev/urandom not readable, please check your platform");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final DataInputStream dis;

    public LinuxSecureRandom() {
        dis = new DataInputStream(urandom);
    }

    @Override
    protected void engineSetSeed(byte[] seed) {
    }

    @Override
    protected void engineNextBytes(byte[] bytes) {
        try {
            dis.readFully(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected byte[] engineGenerateSeed(int numBytes) {
        byte[] bits = new byte[numBytes];
        engineNextBytes(bits);
        return bits;
    }
}
