package com.atm.repository;

import com.atm.domain.Transaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TransactionRepository {
    private final Map<String, Transaction> transactions = new ConcurrentHashMap<>();

    public void save(Transaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
    }

    public List<Transaction> findByAccountId(String accountId) {
        return transactions.values().stream()
                .filter(t -> t.getAccountId().equals(accountId))
                .collect(Collectors.toList());
    }
}