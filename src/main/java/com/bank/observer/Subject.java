package com.bank.observer;

public interface Subject {
    void subscribe(EventType type, Observer observer);
    void unsubscribe(EventType type, Observer observer);
    void notifyObservers(Event event);
}