package com.atm.domain;

import java.math.BigDecimal;

public class WithdrawalTransaction extends Transaction {
    public WithdrawalTransaction(String transactionId, String accountId, BigDecimal amount) {
        super(transactionId, accountId, amount);
    }

    @Override
    public String getType() {
        return "WITHDRAWAL";
    }
}