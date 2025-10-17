package com.atm.domain;

public class Card {
    private String cardNumber;
    private String accountId;
    private String userId;

    public Card(String cardNumber, String accountId, String userId) {
        this.cardNumber = cardNumber;
        this.accountId = accountId;
        this.userId = userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getUserId() {
        return userId;
    }
}