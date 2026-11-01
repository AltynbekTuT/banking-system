package com.bank.visitor;

import com.bank.domain.*;

public class TotalInterestVisitor implements BankVisitor<Double> {

    @Override
    public Double visit(Bank bank) {
        double total = 0;
        for (Branch b : bank.getBranches()) {
            total += b.accept(this);
        }
        return total;
    }

    @Override
    public Double visit(Branch branch) {
        double total = 0;
        for (BankElement e : branch.getElements()) {
            total += e.accept(this);
        }
        return total;
    }

    @Override
    public Double visit(Account account) { return 0.0; }

    @Override
    public Double visit(Card card) { return 0.0; }

    @Override
    public Double visit(Loan loan) {
        return loan.getAmount() * (loan.getInterestRate() / 100.0);
    }

    @Override
    public Double visit(Deposit deposit) {
        return deposit.getAmount() * (deposit.getInterestRate() / 100.0);
    }
}