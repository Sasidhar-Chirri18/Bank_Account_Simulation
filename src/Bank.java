import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String ownerName, double initialDeposit) {
        Account a = new Account(ownerName);
        if (initialDeposit > 0) a.deposit(initialDeposit);
        accounts.put(a.getAccountNumber(), a);
        return a;
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void listAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts in the bank yet.");
            return;
        }
        accounts.values().forEach(System.out::println);
    }
}
