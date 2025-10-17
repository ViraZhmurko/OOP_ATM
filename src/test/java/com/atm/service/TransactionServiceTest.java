package com.atm.service;

import com.atm.domain.Account;
import com.atm.domain.Transaction;
import com.atm.repository.AccountRepository;
import com.atm.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private TransactionService transactionService;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository();
        transactionService = new TransactionService(accountRepository, transactionRepository);

        Account account = new Account("acc2", "user1", new BigDecimal("500.00"));
        accountRepository.save(account);
    }

    @Test
    void testSuccessfulWithdrawal() {
        boolean result = transactionService.withdraw("acc2", new BigDecimal("100.00"));
        assertTrue(result);
        assertEquals(new BigDecimal("400.00"), accountRepository.findById("acc2").getBalance());

        List<Transaction> transactions = transactionService.getTransactionHistory("acc2");
        assertEquals(1, transactions.size());
        assertEquals("WITHDRAWAL", transactions.get(0).getType());
        assertEquals(new BigDecimal("100.00"), transactions.get(0).getAmount());
    }

    @Test
    void testWithdrawalWithInsufficientFunds() {
        boolean result = transactionService.withdraw("acc2", new BigDecimal("600.00"));
        assertFalse(result);
        assertEquals(new BigDecimal("500.00"), accountRepository.findById("acc2").getBalance());
        assertTrue(transactionService.getTransactionHistory("acc2").isEmpty());
    }

    @Test
    void testWithdrawalFromNonExistentAccount() {
        boolean result = transactionService.withdraw("non-existent-acc", new BigDecimal("100.00"));
        assertFalse(result);
    }
}