import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT }

    private LocalDateTime timestamp;
    private Type type;
    private double amount;
    private double balanceAfter;
    private String details;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction(Type type, double amount, double balanceAfter, String details) {
        this.timestamp = LocalDateTime.now();
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.details = details;
    }

    @Override
    public String toString() {
        return String.format("%s | %-12s | %10.2f | Bal: %10.2f | %s",
                timestamp.format(FMT),
                type,
                amount,
                balanceAfter,
                details == null ? "" : details);
    }
}
