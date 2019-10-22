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

    public String getCurrency () {
        return currency.getCurrencyCode();
    }

    public void deposit(BigDecimal deposit) {
        this.amount = amount.add(deposit);
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
