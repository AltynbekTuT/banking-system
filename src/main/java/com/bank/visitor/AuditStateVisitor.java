package main.java.com.bank.visitor;

public class AuditStateVisitor {
    
}
public interface BankVisitor<R> {
    R visit(Bank bank);
    R visit(Branch branch);
    R visit(Account account);
    R visit(Card card);
    R visit(Loan loan);
    R visit(Deposit deposit);
}