package com.bank.domain;

import com.bank.visitor.BankVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Branch implements BankElement {
    private final String id;
    private final List<BankElement> elements = new ArrayList<>();

    public Branch(String id) {
        this.id = id;
    }

    public void addElement(BankElement element) {
        elements.add(element);
    }

    public String getId() { return id; }
    public List<BankElement> getElements() { return Collections.unmodifiableList(elements); }

    @Override
    public <R> R accept(BankVisitor<R> visitor) {
        return visitor.visit(this);
    }
}