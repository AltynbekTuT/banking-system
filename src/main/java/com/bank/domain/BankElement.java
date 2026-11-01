package com.bank.domain;

import com.bank.visitor.BankVisitor;

public interface BankElement {
    <R> R accept(BankVisitor<R> visitor);
}