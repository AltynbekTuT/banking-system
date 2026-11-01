package com.bank.visitor;

import com.bank.domain.*;

public class JsonExportVisitor implements BankVisitor<String> {

    @Override
    public String visit(Bank bank) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"bank\": \"").append(bank.getName()).append("\",\n  \"branches\": [\n");
        for (int i = 0; i < bank.getBranches().size(); i++) {
            sb.append(bank.getBranches().get(i).accept(this));
            if (i < bank.getBranches().size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n}");
        return sb.toString();
    }

    @Override
    public String visit(Branch branch) {
        StringBuilder sb = new StringBuilder();
        sb.append("    {\n      \"branchId\": \"").append(branch.getId()).append("\",\n      \"elements\": [\n");
        for (int i = 0; i < branch.getElements().size(); i++) {
            sb.append("        ").append(branch.getElements().get(i).accept(this));
            if (i < branch.getElements().size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("      ]\n    }");
        return sb.toString();
    }

    @Override
    public String visit(Account account) {
        return String.format("{\"type\":\"Account\",\"id\":\"%s\",\"balance\":%.2f}", account.getId(), account.getBalance());
    }

    @Override
    public String visit(Card card) {
        return String.format("{\"type\":\"Card\",\"number\":\"%s\",\"limit\":%.2f}", card.getCardNumber(), card.getLimit());
    }

    @Override
    public String visit(Loan loan) {
        return String.format("{\"type\":\"Loan\",\"id\":\"%s\",\"amount\":%.2f}", loan.getLoanId(), loan.getAmount());
    }

    @Override
    public String visit(Deposit deposit) {
        return String.format("{\"type\":\"Deposit\",\"id\":\"%s\",\"amount\":%.2f}", deposit.getDepositId(), deposit.getAmount());
    }
}