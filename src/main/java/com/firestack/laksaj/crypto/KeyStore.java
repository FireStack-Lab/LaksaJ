package com.firestack.laksaj.crypto;

import com.firestack.laksaj.utils.ByteUtil;

import java.io.UnsupportedEncodingException;

public class KeyStore {
    private PBKDF2Wrapper pbkdf2Wrapper;
    private ScryptWrapper scryptWrapper;

    public KeyStore(PBKDF2Wrapper pbkdf2Wrapper, ScryptWrapper scryptWrapper) {
        this.pbkdf2Wrapper = pbkdf2Wrapper;
        this.scryptWrapper = scryptWrapper;
    }

    public byte[] getDerivedKey(byte[] password, KDFParams params) throws UnsupportedEncodingException {
        if (params instanceof PBKDF2Params) {
            PBKDF2Params pbkdf2Params = (PBKDF2Params) params;
            return pbkdf2Wrapper.getDerivedKey(password, ByteUtil.hexStringToByteArray(pbkdf2Params.getSalt()), pbkdf2Params.getCount(), pbkdf2Params.getDkLen());
        } else if (params instanceof ScryptParams) {
            ScryptParams scryptParams = (ScryptParams) params;
            return scryptWrapper.getDerivedKey(password, ByteUtil.hexStringToByteArray(scryptParams.getSalt()), scryptParams.getN(), scryptParams.getR(), scryptParams.getP(), scryptParams.getDkLen());
        } else {
            throw new IllegalArgumentException("unsupport kdf params");
        }
    }
}
