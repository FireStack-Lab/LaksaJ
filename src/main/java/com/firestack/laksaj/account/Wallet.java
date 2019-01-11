package com.firestack.laksaj.account;

import com.firestack.laksaj.crypto.KeyTools;
import com.firestack.laksaj.jsonrpc.Provider;

import java.util.List;
import java.util.Optional;

public class Wallet {
    private List<Account> accounts;
    private Provider provider;
    private Optional<Account> defaultAccount;

    public Wallet(List<Account> accounts, Provider provider) {
        this.accounts = accounts;
        this.provider = provider;

        if (accounts.size() > 0) {
            defaultAccount = Optional.of(accounts.get(0));
        } else {
            Optional.empty();
        }
    }

    public String createAccount() {
        String privateString = KeyTools.generatePrivateKey();
        Account account = new Account(privateString);
        this.accounts.add(account);

        if (!defaultAccount.isPresent()) {
            defaultAccount = Optional.of(accounts.get(0));
        }
        return account.getAddress();
    }

    public String addByPrivateKey(String privateKey) {
        Account account = new Account(privateKey);
        this.accounts.add(account);
        if (!defaultAccount.isPresent()) {
            defaultAccount = Optional.of(accounts.get(0));
        }
        return account.getAddress();
    }
}
