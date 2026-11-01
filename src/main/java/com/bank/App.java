package com.bank;

import com.bank.domain.*;
import com.bank.listeners.*;
import com.bank.observer.*;

public class App {
    public static void main(String[] args) {
        Bank bank = new Bank("Global Bank");
        Branch branch1 = new Branch("BRANCH-01");
        branch1.addElement(new Account("ACC-101", 15000.0));
        branch1.addElement(new Loan("LOAN-201", 100000.0, 12.5));
        
        bank.addBranch(branch1);

        EventBus eventBus = new EventBus();
        
        FraudDetector fraudDetector = new FraudDetector(eventBus);
        BranchInspector inspector = new BranchInspector(bank);

        eventBus.subscribe(EventType.TRANSACTION_CREATED, fraudDetector);
        eventBus.subscribe(EventType.FRAUD_SUSPECTED, inspector);

        eventBus.notifyObservers(new Event(EventType.TRANSACTION_CREATED, "SUSPICIOUS transfer of $90,000", "BRANCH-01"));
    }
}