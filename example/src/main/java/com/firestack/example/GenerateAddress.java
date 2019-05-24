package com.firestack.example;

import com.firestack.laksaj.crypto.KeyTools;
import com.firestack.laksaj.utils.ByteUtil;
import org.web3j.crypto.ECKeyPair;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public class GenerateAddress {
    //How to generate large amount of addresses
    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        int n = 0;
        while (n < 100) {
            System.out.println("--------------------------");
            System.out.println("generate nth keypair:");
            ECKeyPair keyPair = KeyTools.generateKeyPair();
            BigInteger privateInteger = keyPair.getPrivateKey();
            BigInteger publicInteger = keyPair.getPublicKey();
            System.out.println("private key is: " + privateInteger.toString(16));
            System.out.println("public key is: " + publicInteger.toString(16));
            System.out.println("address is: " + KeyTools.getAddressFromPublicKey(ByteUtil.byteArrayToHexString(publicInteger.toByteArray())));
        }
    }
}
