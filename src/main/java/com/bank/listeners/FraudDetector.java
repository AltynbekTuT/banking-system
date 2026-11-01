package com.bank.listeners;

import com.bank.observer.*;

public class FraudDetector implements Observer {
    private final Subject eventBus;

    public FraudDetector(Subject eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void onEvent(Event event) {
        if (event.getPayload().contains("SUSPICIOUS")) {
            Event alert = new Event(EventType.FRAUD_SUSPECTED, "Fraud alert for: " + event.getPayload(), event.getTargetBranchId());
            eventBus.notifyObservers(alert);
        }
    }
}