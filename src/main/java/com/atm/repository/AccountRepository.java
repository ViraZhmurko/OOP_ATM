package com.atm.repository;

import com.atm.domain.Account;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public Account findById(String id) {
        return accounts.get(id);
    }

    public void save(Account account) {
        accounts.put(account.getId(), account);
    }
}