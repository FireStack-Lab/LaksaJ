package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;

import java.io.UnsupportedEncodingException;

public class KeyStore {

    public static byte[] getDerivedKey(byte[] password, KDFParams params) throws UnsupportedEncodingException {
        if (params instanceof PBKDF2Params) {
            PBKDF2Params pbkdf2Params = (PBKDF2Params) params;
            return PBKDF2Wrapper.getDerivedKey(password, ByteUtil.hexStringToByteArray(pbkdf2Params.getSalt()), pbkdf2Params.getCount(), pbkdf2Params.getDkLen());
        } else if (params instanceof ScryptParams) {
            ScryptParams scryptParams = (ScryptParams) params;
            return ScryptWrapper.getDerivedKey(password, ByteUtil.hexStringToByteArray(scryptParams.getSalt()), scryptParams.getN(), scryptParams.getR(), scryptParams.getP(), scryptParams.getDkLen());
        } else {
            throw new IllegalArgumentException("unsupport kdf params");
        }
    }
}
