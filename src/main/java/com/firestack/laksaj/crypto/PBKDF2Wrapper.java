package com.firestack.laksaj.crypto;


import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;

import java.io.UnsupportedEncodingException;

public class PBKDF2Wrapper {
    public byte[] getDerivedKey(byte[] password, byte[] salt, int iterationCount, int keySize) throws UnsupportedEncodingException {
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator(new SHA256Digest());
        generator.init(password, salt, iterationCount);
        return ((KeyParameter) generator.generateDerivedParameters(keySize * 8)).getKey();
    }
}
