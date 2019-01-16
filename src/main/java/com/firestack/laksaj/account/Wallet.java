package com.firestack.laksaj.account;

import com.firestack.laksaj.crypto.KeyTools;
import com.firestack.laksaj.crypto.Schnorr;
import com.firestack.laksaj.crypto.Signature;
import com.firestack.laksaj.jsonrpc.Provider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TxParams;
import com.firestack.laksaj.utils.ByteUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * all address should be upper case
 */
public class Wallet {
    private Map<String, Account> accounts = new HashMap<>();
    private Provider provider;
    private Optional<Account> defaultAccount;

    public Wallet() {
        defaultAccount = Optional.empty();
        provider = new Provider("https://api.zilliqa.com/");
    }

    public Wallet(Map<String, Account> accounts, Provider provider) {
        this.accounts = accounts;
        this.provider = provider;

        if (accounts.size() > 0) {
            defaultAccount = Optional.of(accounts.values().iterator().next());
        } else {
            Optional.empty();
        }
    }

    public String createAccount() {
        String privateString = KeyTools.generatePrivateKey();
        Account account = new Account(privateString);
        this.accounts.put(account.getAddress().toUpperCase(), account);

        if (!defaultAccount.isPresent()) {
            defaultAccount = Optional.of(account);
        }
        return account.getAddress();
    }

    public String addByPrivateKey(String privateKey) {
        Account account = new Account(privateKey);
        this.accounts.put(account.getAddress(), account);
        if (!defaultAccount.isPresent()) {
            defaultAccount = Optional.of(account);
        }
        return account.getAddress();
    }

    public String addByKeyStore(String keystore, String passphrase) throws Exception {
        Account account = Account.fromFile(keystore, passphrase);
        this.accounts.put(account.getAddress(), account);

        if (!defaultAccount.isPresent()) {
            defaultAccount = Optional.of(account);
        }
        return account.getAddress();
    }

    public void setDefault(String address) {
        this.defaultAccount = Optional.of(accounts.get(address));
    }

    public void remove(String address) {
        Account toRemove = accounts.get(address);
        if (null != toRemove) {
            accounts.remove(address);
            if (defaultAccount.isPresent() && defaultAccount.get().getAddress().equals(toRemove.getAddress())) {
                if (!accounts.values().isEmpty()) {
                    defaultAccount = Optional.of(accounts.values().iterator().next());
                } else {
                    defaultAccount = Optional.empty();
                }
            }
        }
    }

    public Transaction sign(Transaction transaction) {

        TxParams txParams = transaction.toTransactionParam();

        if (Objects.nonNull(txParams) && !txParams.getSenderPubKey().isEmpty()) {
            String address = KeyTools.getAddressFromPublicKey(txParams.getSenderPubKey()).toUpperCase();
            Account account = accounts.get(address);
            if (Objects.isNull(account)) {
                throw new IllegalArgumentException("Could not sign the transaction with" + address + "  as it does not exist");
            }
            return signWith(transaction, account);
        }

        if (!this.defaultAccount.isPresent()) {
            throw new IllegalArgumentException("This wallet has no default account.");
        }

        return this.signWith(transaction, this.defaultAccount.get());

    }

    public Transaction signWith(Transaction tx, Account signer) {
        Provider.BalanceResult result;
        if (Objects.isNull(signer)) {
            throw new IllegalArgumentException("account not exists");
        }
        if (Objects.isNull(tx.getNonce()) || tx.getNonce().isEmpty()) {
            try {
                result = this.provider.getBalance(signer.getAddress());
                tx.setNonce(result.getNonce());
            } catch (IOException e) {
                throw new IllegalArgumentException("cannot get nonce", e);
            }
        }

        tx.setSenderPubKey(signer.getPublicKey());
        byte[] message = tx.bytes();
        System.out.println("message is: " + ByteUtil.byteArrayToHexString(message));
        byte[] privateKey = ByteUtil.hexStringToByteArray(signer.getPrivateKey());
        System.out.println("private key is: " + ByteUtil.byteArrayToHexString(privateKey));
        byte[] publicKey = ByteUtil.hexStringToByteArray(signer.getPublicKey());
        System.out.println("public key is: " + ByteUtil.byteArrayToHexString(publicKey));
        Signature signature = Schnorr.sign(message, privateKey, publicKey);
        tx.setSignature(signature.toString().toLowerCase());
        return tx;
    }

}
