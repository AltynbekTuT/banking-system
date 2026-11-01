package com.bank.visitor;
import com.bank.domain.*;

import main.java.com.bank.domain.Account;
import main.java.com.bank.domain.Bank;
import main.java.com.bank.domain.Branch;
import main.java.com.bank.domain.Deposit;
import main.java.com.bank.domain.Loan;

public interface BankVisitor<R> {
    R visit(Bank bank);
    R visit(Branch branch);
    R visit(Account account);
    R visit(Card card);
    R visit(Loan loan);
    R visit(Deposit deposit);
}