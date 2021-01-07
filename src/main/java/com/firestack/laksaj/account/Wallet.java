package com.firestack.laksaj.account;

import com.firestack.laksaj.crypto.KeyTools;
import com.firestack.laksaj.crypto.Schnorr;
import com.firestack.laksaj.crypto.Signature;
import com.firestack.laksaj.jsonrpc.HttpProvider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TxParams;
import com.firestack.laksaj.utils.Bech32;
import com.firestack.laksaj.utils.Validation;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;


/**
 * all address should be upper case
 */
public class Wallet {
    private Map<String, Account> accounts = new HashMap<>();
    private HttpProvider provider;
    private Optional<Account> defaultAccount;

    public Wallet() {
        defaultAccount = Optional.empty();
        provider = new HttpProvider("https://api.zilliqa.com/");
    }

    public void setProvider(HttpProvider provider) {
        this.provider = provider;
    }

    public Wallet(Map<String, Account> accounts, HttpProvider provider) {
        this.accounts = accounts;
        this.provider = provider;

        if (accounts.size() > 0) {
            defaultAccount = Optional.of(accounts.values().iterator().next());
        } else {
            Optional.empty();
        }
    }

    public String createAccount() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        Account account = new Account(KeyTools.generateKeyPair());
        this.accounts.put(account.getAddress().toUpperCase(), account);

        if (!defaultAccount.isPresent()) {
            defaultAccount = Optional.of(account);
        }
        return account.getAddress();
    }

    public String addByPrivateKey(String privateKey) throws NoSuchAlgorithmException {
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

    public List<Transaction> batchSign(List<Transaction> transactions) throws Exception {
        for (int i = 0; i < transactions.size(); i++) {
            this.sign(transactions.get(i));
        }
        return transactions;
    }

    public Transaction sign(Transaction transaction) throws Exception {

        if (transaction.getToAddr().startsWith("0x") || transaction.getToAddr().startsWith("0X")) {
            transaction.setToAddr(transaction.getToAddr().substring(2));
        }

        if (!Validation.isBech32(transaction.getToAddr()) && !Validation.isValidChecksumAddress("0x" + transaction.getToAddr())) {
            throw new Exception("not checksum address or bech32");
        }


        if (Validation.isBech32(transaction.getToAddr())) {
            transaction.setToAddr(Bech32.fromBech32Address(transaction.getToAddr()));
        }

        if (Validation.isValidChecksumAddress("0x" + transaction.getToAddr())) {
            transaction.setToAddr("0x" + transaction.getToAddr());
        }


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

    public Transaction signWith(Transaction tx, Account signer) throws IOException, NoSuchAlgorithmException {
        HttpProvider.BalanceResult result;
        if (Objects.isNull(signer)) {
            throw new IllegalArgumentException("account not exists");
        }
        if (Objects.isNull(tx.getNonce()) || tx.getNonce().isEmpty()) {
            try {
                result = this.provider.getBalance(signer.getAddress()).getResult();
                tx.setNonce(String.valueOf(Integer.valueOf(result.getNonce()) + 1));
            } catch (IOException e) {
                throw new IllegalArgumentException("cannot get nonce", e);
            }
        }
        tx.setSenderPubKey(signer.getPublicKey());
        byte[] message = tx.bytes();
        Signature signature = Schnorr.sign(signer.getKeys(), message);
        tx.setSignature(signature.toString().toLowerCase());
        return tx;
    }

    public static int pack(int a, int b) {
        return (a << 16) + b;
    }

}
