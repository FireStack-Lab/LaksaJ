package com.firestack.laksaj.account;

import com.firestack.laksaj.crypto.KeyTools;
import com.firestack.laksaj.jsonrpc.Provider;
import com.firestack.laksaj.transaction.Transaction;
import com.firestack.laksaj.transaction.TxParams;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Wallet {
    private Map<String, Account> accounts = new HashMap<>();
    private Provider provider;
    private Optional<Account> defaultAccount;

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
        this.accounts.put(account.getAddress(), account);

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
//
//    public Transaction signWith(Transaction tx, String account) {
//        Account signer = accounts.get(account);
//        Provider.BalanceResult result;
//        TxParams txParams = tx.toTransactionParam();
//        if (Objects.isNull(signer)) {
//            throw new IllegalArgumentException("account not exists");
//        }
//        if (tx.getNonce().isEmpty()) {
//            try {
//                result = this.provider.getBalance(account);
//                txParams.setNonce(result.getNonce());
//            } catch (IOException e) {
//                throw new IllegalArgumentException("cannot get nonce", e);
//            }
//        }
//        txParams.setSenderPubKey(signer.getPublicKey());
//        txParams.setSignature();
//
//    }


}
