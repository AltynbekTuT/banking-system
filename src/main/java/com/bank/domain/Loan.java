package com.bank.domain;

import com.bank.visitor.BankVisitor;

public class Loan implements BankElement {
    private final String loanId;
    private final double amount;
    private final double interestRate;

    public Loan(String loanId, double amount, double interestRate) {
        this.loanId = loanId;
        this.amount = amount;
        this.interestRate = interestRate;
    }

    public String getLoanId() { return loanId; }
    public double getAmount() { return amount; }
    public double getInterestRate() { return interestRate; }

    @Override
    public <R> R accept(BankVisitor<R> visitor) {
        return visitor.visit(this);
    }
}