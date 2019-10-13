package owlmoney.model.bank;

import java.text.DecimalFormat;

import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.ui.Ui;

/**
 * The Bank class which is abstract where various bank types will inherit from given that it is abstract.
 */

public abstract class Bank {
    String type;
    private String accountName;
    private double currentAmount;
    TransactionList transactions;

    /**
     * Allows the child class to create an instance with name and current amount.
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
    double getCurrentAmount() {
        return this.currentAmount;
    }

    /**
     * Gets the details of the bank account which consist of name and amount.
     *
     * @return accountName and currentAmount.
     */
    public String getDescription() {
        return "Account name: " + accountName + "\nType: " + getType() + "\nCurrent Amount: "
                + new DecimalFormat("0.00").format(currentAmount);
    }

    /**
     * Deducts from amount in bank.
     *
     * @param amount Amount to be deducted.
     */
    void deductFromAmount(double amount) {
        this.currentAmount -= amount;
    }

    /**
     * Adds to amount in bank.
     *
     * @param amount Amount to be added.
     */
    void addToAmount(double amount) {
        this.currentAmount += amount;
    }

    /**
     * Abstract method which adds a new expenditure to the current bank account.
     *
     * @param exp Expenditure to be added.
     * @param ui  Ui of OwlMoney
     */
    public abstract void addInExpenditure(Transaction exp, Ui ui);

    /*
    public abstract void listAllTransaction(Ui ui);
    */

    /**
     * Abstract method which deletes an expenditure from the current bank account.
     *
     * @param exNum Transaction number.
     * @param ui    Ui of OwlMoney.
     */
    public abstract void deleteExpenditure(int exNum, Ui ui);

    /**
     * Edits expenditure in the current bank account.
     *
     * @param expNum   Transaction number.
     * @param desc     New description.
     * @param amount   New amount.
     * @param date     New date.
     * @param category New category.
     * @param ui       Ui of OwlMoney.
     */
    void editExpenditureDetails(int expNum, String desc, String amount, String date, String category, Ui ui) {
        ui.printError("This account does not support this feature");
    }

    /**
     * Edits deposit in the current bank account.
     *
     * @param expNum Transaction number.
     * @param desc   New description.
     * @param amount New amount.
     * @param date   New date.
     * @param ui     Ui of OwlMoney.
     */
    void editDepositDetails(int expNum, String desc, String amount, String date, Ui ui) {
        ui.printError("This account does not support this feature");
    }

    /**
     * Sets the income of the current bank.
     *
     * @param newIncome Income to set.
     */
    void setIncome(double newIncome) {
        //for Saving class
    }

    /**
     * Lists expenditures from the current bank.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of expenditure to list.
     */
    void listAllExpenditure(Ui ui, int displayNum) {
        ui.printError("This account does not support this feature");
    }

    /**
     * Lists deposits from the current bank.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of deposits to list.
     */
    void listAllDeposit(Ui ui, int displayNum) {
        ui.printError("This account does not support this feature");
    }

    /**
     * Adds a new deposit to the current bank account.
     *
     * @param dep Deposit to add.
     * @param ui  Ui of OwlMoney.
     */
    void addDepositTransaction(Transaction dep, Ui ui) {
        ui.printError("This account does not support this feature");
    }

    /**
     * Deletes a deposit from the current bank account.
     *
     * @param index Transaction number.
     * @param ui    Ui of OwlMoney.
     */
    void deleteDepositTransaction(int index, Ui ui) {
        ui.printError("This account does not support this feature");
    }
}
