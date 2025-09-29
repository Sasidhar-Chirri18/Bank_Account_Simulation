import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();

        // create a sample accounts
        Account a1 = bank.createAccount("Sasidhar", 1000.0);
        Account a2 = bank.createAccount("Vishnu", 500.0);
        System.out.println("Sample accounts created:");
        bank.listAccounts();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Choose option: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1": // create account
                        System.out.print("Owner name: ");
                        String owner = sc.nextLine().trim();
                        System.out.print("Initial deposit (0 for none): ");
                        double init = readDouble(sc);
                        Account acc = bank.createAccount(owner, init);
                        System.out.println("Created: " + acc);
                        break;

                    case "2": // deposit
                        System.out.print("Account number: ");
                        String accNo = sc.nextLine().trim();
                        Account depositAcc = bank.getAccount(accNo);
                        if (depositAcc == null) { System.out.println("Account not found."); break; }
                        System.out.print("Amount to deposit: ");
                        double damt = readDouble(sc);
                        depositAcc.deposit(damt);
                        System.out.printf("Deposit successful. New balance: %.2f%n", depositAcc.getBalance());
                        break;

                    case "3": // withdraw
                        System.out.print("Account number: ");
                        String wAccNo = sc.nextLine().trim();
                        Account withdrawAcc = bank.getAccount(wAccNo);
                        if (withdrawAcc == null) { System.out.println("Account not found."); break; }
                        System.out.print("Amount to withdraw: ");
                        double wamt = readDouble(sc);
                        withdrawAcc.withdraw(wamt);
                        System.out.printf("Withdrawal successful. New balance: %.2f%n", withdrawAcc.getBalance());
                        break;

                    case "4": // transfer
                        System.out.print("From account number: ");
                        String from = sc.nextLine().trim();
                        Account fromAcc = bank.getAccount(from);
                        if (fromAcc == null) { System.out.println("Source account not found."); break; }
                        System.out.print("To account number: ");
                        String to = sc.nextLine().trim();
                        Account toAcc = bank.getAccount(to);
                        if (toAcc == null) { System.out.println("Destination account not found."); break; }
                        System.out.print("Amount to transfer: ");
                        double tamt = readDouble(sc);
                        boolean ok = fromAcc.transferTo(toAcc, tamt);
                        if (ok) System.out.println("Transfer successful.");
                        else System.out.println("Transfer failed: insufficient funds.");
                        break;

                    case "5": // statement
                        System.out.print("Account number: ");
                        String sAccNo = sc.nextLine().trim();
                        Account sAcc = bank.getAccount(sAccNo);
                        if (sAcc == null) { System.out.println("Account not found."); break; }
                        sAcc.printStatement();
                        break;

                    case "6":
                        bank.listAccounts();
                        break;

                    case "7":
                        running = false;
                        System.out.println("Exiting. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n==== Simple Bank Menu ====");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer");
        System.out.println("5. Print Statement");
        System.out.println("6. List Accounts");
        System.out.println("7. Exit");
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Enter again: ");
            }
        }
    }
}
