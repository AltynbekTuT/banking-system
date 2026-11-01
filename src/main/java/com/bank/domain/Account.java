package com.bank.domain;

import com.bank.visitor.BankVisitor;

public class Account implements BankElement {
    private final String id;
    private final double balance;

    public Account(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public String getId() { return id; }
    public double getBalance() { return balance; }

    @Override
    public <R> R accept(BankVisitor<R> visitor) {
        return visitor.visit(this);
    }
}