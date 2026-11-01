package com.bank.domain;

import com.bank.visitor.BankVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bank implements BankElement {
    private final String name;
    private final List<Branch> branches = new ArrayList<>();

    public Bank(String name) {
        this.name = name;
    }

    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    public String getName() { return name; }
    public List<Branch> getBranches() { return Collections.unmodifiableList(branches); }

    @Override
    public <R> R accept(BankVisitor<R> visitor) {
        return visitor.visit(this);
    }
}