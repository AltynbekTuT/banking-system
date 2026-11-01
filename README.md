# Assignment 6: Banking Transactions System (Observer & Visitor Patterns)

This repository contains a clean, robust, and thread-safe implementation of a banking transactions domain utilizing the **Observer** and **Visitor** design patterns in Java 17, as per the Assignment 6 requirements.

---

## System Architecture

The architecture consists of two primary sides linked together by domain integration listeners:

1. **Observer Side (Live Data Stream):**
   - **`EventBus` (Subject):** Manages subscriber registration and notifies listeners of domain events (`TRANSACTION_CREATED`, `FRAUD_SUSPECTED`, etc.).
   - **`Event`:** An immutable value object storing event payload, target branch metadata, and timestamps.
   - **`FraudDetector` (Chained Observer):** Listens for raw transaction events, aggregates suspicious activity, and re-publishes a `FRAUD_SUSPECTED` event into the system.

2. **Visitor Side (Composite Domain Tree):**
   - **Tree Structure (3 levels):** `Bank` (Root) ➔ `Branch` (Container) ➔ `Account`, `Card`, `Loan`, `Deposit` (Leaf elements).
   - **`BankVisitor<R>`:** Generic interface supporting double dispatch (`accept(visitor)`) across all composite and leaf elements.
   - **Concrete Visitors:**
     - `TotalInterestVisitor`: Calculates annual projected interest revenues/payouts.
     - `AuditStateVisitor`: Accumulates domain stats (total accounts, capital, active loans) across the tree.
     - `HighRiskFilterVisitor`: Traverses elements and returns a list of high-risk loans (exceeding $50,000).
     - `JsonExportVisitor`: Generates a custom, hand-crafted JSON representation of the tree with correct nesting.

3. **Integration Link (`Observer ➔ Visitor`):**
   - **`BranchInspector`:** An Observer listener that responds to `FRAUD_SUSPECTED` events by triggering a `HighRiskFilterVisitor` **exclusively on the target branch subtree**, preventing full-tree lockups or unnecessary global traversals.

---

## Design Decisions & Trade-offs

### 1. Push vs. Pull Model (Observer Pattern)
* **Decision:** Selected the **Push Model**.
* **Justification:** Events push self-contained, immutable data directly to observers. This prevents observers from needing back-references to the `Subject` or making concurrent read requests back to shared state, reducing thread contention and race conditions during high-throughput transaction streams.

### 2. Traversal Control: Visitor vs. Composite Tree
* **Decision:** Traversal is controlled directly by the **Visitor** implementations.
* **Justification:** Keeping elements purely focused on data holding (POJOs) adheres to Clean Code principles (ch. 11–12). Visitors can selectively decide whether to traverse child nodes or short-circuit early based on custom execution logic (e.g., filtering specific subtrees).

---

## Trade-off Proof (Git Commit Analysis)

The project includes two specific commits demonstrating the architectural costs and benefits of the Visitor pattern:

* **Commit 1: Added New Visitor (`JsonExportVisitor`)**
  * **Files Changed:** `JsonExportVisitor.java`
  * **Impact/Cost:** **Extremely low.** Added a brand-new feature without touching or recompiling a single domain element class (`Account`, `Loan`, `Branch`, etc.).

* **Commit 2: Added New Element Type (`Deposit`)**
  * **Files Changed:** `Deposit.java`, `BankVisitor.java`, `TotalInterestVisitor.java`, `AuditStateVisitor.java`, `HighRiskFilterVisitor.java`, `JsonExportVisitor.java`.
  * **Impact/Cost:** **High.** Adding a single leaf node forced modifications across the core Visitor interface and **every existing visitor implementation**. This demonstrates why Visitor should be avoided if element hierarchies change frequently.

---

## Thread-Safety & Concurrency (Clean Code Ch. 13)

* **Lock-Free Event Registry:** `EventBus` utilizes `ConcurrentHashMap` with `CopyOnWriteArraySet` for observer registration. Listeners can safely subscribe or unsubscribe dynamically (even from within their own event handler execution) without throwing `ConcurrentModificationException` or blocking event publication.
* **Graceful Shutdown:** Tested using an `ExecutorService` with configurable thread counts and `CountDownLatch` synchronization to ensure no event loss or hanging producer threads during concurrent bursts.

---

## Testing

The test suite includes JUnit 5 tests covering domain state, visitor computations, and thread safety:

* **Stress Test (`ConcurrencyStressTest`):** Executes 4 concurrent producer threads emitting 1,000 events each (4,000 total events) to verify exact event counts and data consistency under load.

---

## Tech Stack
* **Language:** Java 17
* **Testing:** JUnit 5
* **Libraries:** Pure JDK (no external event-bus or JSON/XML dependencies)