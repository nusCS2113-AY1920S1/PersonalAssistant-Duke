package owlmoney.model.bank;

import owlmoney.model.transaction.Transaction;
import owlmoney.ui.Ui;

/**
 * The Bank class which is abstract where various bank types will inherit from given that it is abstract.
 */

public abstract class Bank {
    String type;
    private String accountName;
    private double currentAmount;

    /**
     * Constructor that allows the child class to create an instance with name and current amount.
     *
     * @param name          A name of the bank account.
     * @param currentAmount The amount of money in the bank on initial creation.
     */
    Bank(String name, double currentAmount) {
        this.accountName = name;
        this.currentAmount = currentAmount;
    }

    void setAccountName(String newName) {
        this.accountName = newName;
    }

    String getType() {
        return type;
    }

    void setCurrentAmount(double newAmount) {
        this.currentAmount = newAmount;
    }

    /**
     * Gets the account name of the instance of the bank account.
     *
     * @return The accountName of the bank account.
     */
    String getAccountName() {
        return this.accountName;
    }

    /**
     * Gets the amount of money in the instance of the bank account.
     *
     * @return The currentAmount of money in the bank account.
     */
    public double getCurrentAmount() {
        return this.currentAmount;
    }

    /**
     * Gets the details of the bank account which consist of name and amount.
     *
     * @return accountName and currentAmount.
     */
    public String getDescription() {
        return "Account name: " + accountName + "\nCurrent Amount: " + currentAmount;
    }

    void deductFromAmount(double amount) {
        this.currentAmount -= amount;
    }

    void addToAmount(double amount) {
        this.currentAmount += amount;
    }

    public abstract void addInExpenditure(Transaction exp, Ui ui);

    public abstract void listAllTransaction(Ui ui);

    public abstract void deleteExpenditure(int exNum, Ui ui);
}
