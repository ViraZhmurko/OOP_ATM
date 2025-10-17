package com.atm.service;

import com.atm.domain.Account;
import com.atm.domain.Transaction;
import com.atm.domain.WithdrawalTransaction;
import com.atm.repository.AccountRepository;
import com.atm.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public boolean withdraw(String accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId);
        if (account != null && account.withdraw(amount)) {
            Transaction transaction = new WithdrawalTransaction(UUID.randomUUID().toString(), accountId, amount);
            transactionRepository.save(transaction);
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    public List<Transaction> getTransactionHistory(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}