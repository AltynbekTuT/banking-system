package com.bank.domain;

import com.bank.visitor.BankVisitor;

public class Card implements BankElement {
    private final String cardNumber;
    private final double limit;

    public Card(String cardNumber, double limit) {
        this.cardNumber = cardNumber;
        this.limit = limit;
    }

    public String getCardNumber() { return cardNumber; }
    public double getLimit() { return limit; }

    @Override
    public <R> R accept(BankVisitor<R> visitor) {
        return visitor.visit(this);
    }
}