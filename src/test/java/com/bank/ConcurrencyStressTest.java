package com.bank;

import com.bank.observer.Event;
import com.bank.observer.EventBus;
import com.bank.observer.EventType;
import com.bank.observer.Observer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcurrencyStressTest {

    @Test
    void testConcurrentEventProcessing() throws InterruptedException {
        EventBus eventBus = new EventBus();
        AtomicInteger count = new AtomicInteger(0);

        Observer counterListener = event -> count.incrementAndGet();
        eventBus.subscribe(EventType.TRANSACTION_CREATED, counterListener);

        int threads = 4;
        int eventsPerThread = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < eventsPerThread; j++) {
                    eventBus.notifyObservers(new Event(EventType.TRANSACTION_CREATED, "Data", "BRANCH-01"));
                }
                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();

        assertEquals(threads * eventsPerThread, count.get());
    }
}