package com.bank.visitor;

import com.bank.domain.*;

public class AuditStateVisitor implements BankVisitor<Void> {
    private int totalAccounts = 0;
    private int totalLoans = 0;
    private double totalCapital = 0;

    @Override
    public Void visit(Bank bank) {
        for (Branch branch : bank.getBranches()) {
            branch.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Branch branch) {
        for (BankElement element : branch.getElements()) {
            element.accept(this);
        }
        return null;
    }

    @Override
    public Void visit(Account account) {
        totalAccounts++;
        totalCapital += account.getBalance();
        return null;
    }

    @Override
    public Void visit(Card card) { return null; }

    @Override
    public Void visit(Loan loan) {
        totalLoans++;
        return null;
    }

    @Override
    public Void visit(Deposit deposit) {
        totalCapital += deposit.getAmount();
        return null;
    }

    public int getTotalAccounts() { return totalAccounts; }
    public int getTotalLoans() { return totalLoans; }
    public double getTotalCapital() { return totalCapital; }
}