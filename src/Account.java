import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String ownerName;
    private double balance;
    private List<Transaction> transactions;

    private static long accountSequence = 1000000000L; // starting account number

    public Account(String ownerName) {
        this.accountNumber = String.valueOf(accountSequence++);
        this.ownerName = ownerName;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() { return balance; }

    public synchronized void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Deposit amount must be positive.");
        balance += amount;
        transactions.add(new Transaction(Transaction.Type.DEPOSIT, amount, balance, "Deposit"));
    }

    public synchronized void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Withdrawal amount must be positive.");
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds.");
        balance -= amount;
        transactions.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, balance, "Withdrawal"));
    }

    public synchronized boolean transferTo(Account to, double amount) {
        if (to == null) throw new IllegalArgumentException("Destination account cannot be null.");
        if (to.getAccountNumber().equals(this.accountNumber)) throw new IllegalArgumentException("Cannot transfer to same account.");
        if (amount <= 0) throw new IllegalArgumentException("Transfer amount must be positive.");
        if (amount > balance) return false; // insufficient funds

        // debit from this
        balance -= amount;
        transactions.add(new Transaction(Transaction.Type.TRANSFER_OUT, amount, balance, "To: " + to.getAccountNumber()));

        // credit destination
        to.receiveTransfer(this, amount);
        return true;
    }

    // private helps to receive transfer
    private synchronized void receiveTransfer(Account from, double amount) {
        this.balance += amount;
        this.transactions.add(new Transaction(Transaction.Type.TRANSFER_IN, amount, this.balance, "From: " + from.getAccountNumber()));
    }

    public void printStatement() {
        System.out.println("\nStatement for Account " + accountNumber + " - " + ownerName);
        System.out.println("-------------------------------------------------------------------");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.printf("Current balance: %.2f%n%n", balance);
    }

    @Override
    public String toString() {
        return String.format("Account[%s] Owner: %s Balance: %.2f", accountNumber, ownerName, balance);
    }
}

