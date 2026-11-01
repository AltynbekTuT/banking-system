package com.bank.domain;

import com.bank.visitor.BankVisitor;

public class Deposit implements BankElement {
    private final String depositId;
    private final double amount;
    private final double interestRate;

    public Deposit(String depositId, double amount, double interestRate) {
        this.depositId = depositId;
        this.amount = amount;
        this.interestRate = interestRate;
    }

    public String getDepositId() { return depositId; }
    public double getAmount() { return amount; }
    public double getInterestRate() { return interestRate; }

    @Override
    public <R> R accept(BankVisitor<R> visitor) {
        return visitor.visit(this);
    }
}