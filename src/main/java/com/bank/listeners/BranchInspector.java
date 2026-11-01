package com.bank.listeners;

import com.bank.domain.Bank;
import com.bank.domain.Branch;
import com.bank.domain.Loan;
import com.bank.observer.Event;
import com.bank.observer.Observer;
import com.bank.visitor.HighRiskFilterVisitor;

import java.util.List;

public class BranchInspector implements Observer {
    private final Bank bank;

    public BranchInspector(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void onEvent(Event event) {
        String branchId = event.getTargetBranchId();
        
        Branch targetBranch = bank.getBranches().stream()
                .filter(b -> b.getId().equals(branchId))
                .findFirst()
                .orElse(null);

        if (targetBranch != null) {
            HighRiskFilterVisitor visitor = new HighRiskFilterVisitor();
            List<Loan> riskyLoans = targetBranch.accept(visitor);
            System.out.println("[INSPECTOR] Found " + riskyLoans.size() + " high risk loans in branch " + branchId);
        }
    }
}