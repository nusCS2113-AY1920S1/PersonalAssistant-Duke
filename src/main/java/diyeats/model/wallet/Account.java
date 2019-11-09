package diyeats.model.wallet;

import java.math.BigDecimal;
import java.util.Currency;
//@@author GaryStu

/**
 * Account is a public class that represent's a user's money account.
 */
public class Account {
    private BigDecimal amount;

    /**
     * this is the constructor that initializes account amount as 0.
     */
    public Account() {
        this.amount = new BigDecimal(0);
    }

    /**
     * this constructor initializes account amount to amount specified.
     * @param amount amount to be assigned to account.
     */
    public Account(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * the getter for the amount.
     * @return amount of money in the account.
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * the setter for the amount.
     * @param amount amount of money in the account.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * deposit amount to account.
     * @param deposit amount to be deposited to account.
     */
    public void deposit(BigDecimal deposit) {
        this.amount = amount.add(deposit);
    }

    /**
     * withdraw amount from the account.
     * @param withdrawal the amount to be withdrawn from account.
     */
    public void withdraw(BigDecimal withdrawal) {
        this.amount = amount.subtract(withdrawal);
    }

    /**
     * check whether the account has sufficient balance after a withdrawal.
     * @param withdrawal the amount to be withdrawn from account.
     * @return <code>true</code> if there is sufficient balance.
     *         Return <code>false</code> if there is insufficient balance.
     */
    public boolean isSufficientBalance(BigDecimal withdrawal) {
        return withdrawal.compareTo(amount) <= 0;
    }

    /**
     * update the account balance based on the transaction.
     * @param transaction transaction recorded.
     */
    public void updateAccountBalance(Transaction transaction) {
        BigDecimal transactionAmount = transaction.getTransactionAmount();
        if (transaction.getType().equals("PAY")) {
            withdraw(transactionAmount);
        } else if (transaction.getType().equals("DEP")) {
            deposit(transactionAmount);
        }
    }
}
