package duke.model.user;

import java.math.BigDecimal;
import java.util.Currency;

public class Account {
    private BigDecimal amount;
    private Currency currency = Currency.getInstance("SGD");

    public Account(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void accountStatus() {
        System.out.println(amount + " " + currency.getCurrencyCode() + "left in the account");
    }

    public void deposit(BigDecimal deposit) {
        this.amount = amount.add(deposit);
        System.out.println(deposit + " " + currency.getCurrencyCode() + " deposited to your account");
    }

    public void withdraw(BigDecimal withdrawal) {
        if (withdrawal.compareTo(amount) < 0) {
            this.amount = amount.subtract(withdrawal);
            System.out.println(withdrawal + " " + currency.getCurrencyCode() + " withdrawn from your account");
        } else {
            System.out.println("Balance insufficient for a " + withdrawal + currency.getCurrencyCode() + " withdrawal");
        }
    }
}
