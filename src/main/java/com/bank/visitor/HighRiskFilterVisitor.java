package com.bank.visitor;

import com.bank.domain.*;
import java.util.ArrayList;
import java.util.List;

public class HighRiskFilterVisitor implements BankVisitor<List<Loan>> {

    @Override
    public List<Loan> visit(Bank bank) {
        List<Loan> result = new ArrayList<>();
        for (Branch b : bank.getBranches()) {
            result.addAll(b.accept(this));
        }
        return result;
    }

    @Override
    public List<Loan> visit(Branch branch) {
        List<Loan> result = new ArrayList<>();
        for (BankElement e : branch.getElements()) {
            result.addAll(e.accept(this));
        }
        return result;
    }

    @Override
    public List<Loan> visit(Account account) { return List.of(); }

    @Override
    public List<Loan> visit(Card card) { return List.of(); }

    @Override
    public List<Loan> visit(Loan loan) {
        if (loan.getAmount() > 50000) {
            return List.of(loan);
        }
        return List.of();
    }

    @Override
    public List<Loan> visit(Deposit deposit) { return List.of(); }
}