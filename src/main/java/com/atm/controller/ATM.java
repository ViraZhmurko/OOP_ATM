package com.atm.controller;

import com.atm.domain.*;
import com.atm.repository.AccountRepository;
import com.atm.repository.CardRepository;
import com.atm.repository.TransactionRepository;
import com.atm.repository.UserRepository;
import com.atm.service.AuthenticationService;
import com.atm.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ATM {
    private final AuthenticationService authService;
    private final TransactionService transactionService;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    private User currentUser;
    private Account currentAccount;

    public ATM(AuthenticationService authService, TransactionService transactionService, CardRepository cardRepository, AccountRepository accountRepository) {
        this.authService = authService;
        this.transactionService = transactionService;
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM!");

        System.out.print("Please insert your card (enter card number): ");
        String cardNumber = scanner.nextLine();

        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        if (authenticateUser(cardNumber, pin)) {
            System.out.println("Authentication successful. Welcome, " + currentUser.getName() + "!");
            showMenu(scanner);
        } else {
            System.out.println("Authentication failed. Please try again.");
        }
    }

    private boolean authenticateUser(String cardNumber, String pin) {
        currentUser = authService.authenticate(cardNumber, pin);
        if (currentUser != null) {
            Card card = cardRepository.findByCardNumber(cardNumber);
            currentAccount = accountRepository.findById(card.getAccountId());
            return true;
        }
        return false;
    }

    private void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Transaction History");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    BigDecimal amount = scanner.nextBigDecimal();
                    withdraw(amount);
                    break;
                case 3:
                    showTransactionHistory();
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void checkBalance() {
        System.out.println("Your current balance is: " + currentAccount.getBalance());
    }

    private void withdraw(BigDecimal amount) {
        if (transactionService.withdraw(currentAccount.getId(), amount)) {
            System.out.println("Withdrawal successful. Please take your cash.");
            System.out.println("Your new balance is: " + currentAccount.getBalance());
        } else {
            System.out.println("Withdrawal failed. Insufficient funds.");
        }
    }

    private void showTransactionHistory() {
        List<Transaction> transactions = transactionService.getTransactionHistory(currentAccount.getId());
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("--- Transaction History ---");
            for (Transaction t : transactions) {
                System.out.println("ID: " + t.getTransactionId() + ", Type: " + t.getType() + ", Amount: " + t.getAmount() + ", Date: " + t.getTimestamp());
            }
            System.out.println("-------------------------");
        }
    }

    public static void main(String[] args) {
        // --- Data Setup ---
        UserRepository userRepository = new UserRepository();
        AccountRepository accountRepository = new AccountRepository();
        CardRepository cardRepository = new CardRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        User user = new User("user1", "John Doe", "1234");
        userRepository.save(user);

        Account account = new Account("acc1", "user1", new BigDecimal("1000.00"));
        accountRepository.save(account);

        Card card = new Card("1111-2222-3333-4444", "acc1", "user1");
        cardRepository.save(card);

        // --- Service Initialization ---
        AuthenticationService authService = new AuthenticationService(cardRepository, userRepository);
        TransactionService transactionService = new TransactionService(accountRepository, transactionRepository);

        // --- ATM Initialization ---
        ATM atm = new ATM(authService, transactionService, cardRepository, accountRepository);
        atm.start();
    }
}