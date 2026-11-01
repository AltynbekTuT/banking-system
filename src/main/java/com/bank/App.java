package com.bank;

import com.bank.domain.*;
import com.bank.listeners.*;
import com.bank.observer.*;
import com.bank.visitor.*;

public class App {
    public static void main(String[] args) {
        // 1. Build the bank composite structure
        Bank bank = new Bank("Global Bank");
        Branch branch1 = new Branch("BRANCH-01");
        branch1.addElement(new Account("ACC-101", 15000.0));
        branch1.addElement(new Loan("LOAN-201", 100000.0, 12.5));
        branch1.addElement(new Deposit("DEP-301", 50000.0, 5.0));
        
        bank.addBranch(branch1);

        // 2. Setup Observer bus and integration listeners
        EventBus eventBus = new EventBus();
        FraudDetector fraudDetector = new FraudDetector(eventBus);
        BranchInspector inspector = new BranchInspector(bank);

        eventBus.subscribe(EventType.TRANSACTION_CREATED, fraudDetector);
        eventBus.subscribe(EventType.FRAUD_SUSPECTED, inspector);

        // --- CONSOLE OUTPUT MENU ---

        System.out.println("=== 1. PATTERN INTEGRATION TEST (OBSERVER -> VISITOR) ===");
        eventBus.notifyObservers(new Event(EventType.TRANSACTION_CREATED, "SUSPICIOUS transfer of $90,000", "BRANCH-01"));

        System.out.println("\n=== 2. JSON EXPORT VISITOR OUTPUT ===");
        JsonExportVisitor jsonVisitor = new JsonExportVisitor();
        System.out.println(bank.accept(jsonVisitor));

        System.out.println("\n=== 3. TOTAL INTEREST VISITOR OUTPUT ===");
        TotalInterestVisitor interestVisitor = new TotalInterestVisitor();
        System.out.println("Total annual interest income: $" + bank.accept(interestVisitor));

        System.out.println("\n=== 4. AUDIT STATE VISITOR OUTPUT ===");
        AuditStateVisitor auditVisitor = new AuditStateVisitor();
        bank.accept(auditVisitor);
        System.out.println("Total Accounts: " + auditVisitor.getTotalAccounts());
        System.out.println("Total Loans: " + auditVisitor.getTotalLoans());
        System.out.println("Total Capital: $" + auditVisitor.getTotalCapital());
    }
}