package duke.model.wallet;

import java.math.BigDecimal;
import java.util.Currency;

public class Account {
    private BigDecimal amount;
    private Currency currency = Currency.getInstance("SGD");

    public Account() {
        this.amount = new BigDecimal(0);
    }

    public Account(int amount) {
        this.amount = new BigDecimal(amount);
    }

    public Account(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency.getCurrencyCode();
    }

    public void deposit(BigDecimal deposit) {
        //System.out.println(deposit + "" + currency.getCurrencyCode() + " deposited");
        this.amount = amount.add(deposit);
    }

    public void withdraw(BigDecimal withdrawal) {
        this.amount = amount.subtract(withdrawal);
    }

    public boolean isSufficientBalance(BigDecimal withdrawal) {
        return withdrawal.compareTo(amount) <= 0;
    }

    public void depositToAccount(BigDecimal depositAmount) {
        deposit(depositAmount);
    }

    public BigDecimal getAccountBalance() {
        return getAmount();
    }

    public void updateAccountBalance(Transaction transaction) {
        BigDecimal transactionAmount = transaction.getTransactionAmount();
        if (transaction.getType().equals("PAY")) {
            withdraw(transactionAmount);
        } else if (transaction.getType().equals("DEP")) {
            deposit(transactionAmount);
        }
    }
}
