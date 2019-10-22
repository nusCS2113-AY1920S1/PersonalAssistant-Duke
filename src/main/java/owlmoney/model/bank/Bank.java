package owlmoney.model.bank;

import java.text.DecimalFormat;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Contains the details of a bank object.
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

    /**
     * Sets the name of the bank account to a new name.
     *
     * @param newName the new name of the bank account.
     */
    void setAccountName(String newName) {
        this.accountName = newName;
    }

    /**
     * Gets the type of the bank account.
     *
     * @return the bank account type.
     */
    String getType() {
        return type;
    }

    /**
     * Sets the bank account to a new amount.
     *
     * @param newAmount the new amount in the bank account.
     */
    void setCurrentAmount(double newAmount) {
        this.currentAmount = newAmount;
    }

    /**
     * Gets the account name of the instance of the bank account.
     *
     * @return The accountName of the bank account.
     */
    public String getAccountName() {
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
        return "Account name: " + accountName + "\nType: " + getType() + "\nCurrent Amount: $"
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
     * Adds a new expenditure to the current bank account.
     *
     * @param exp      Expenditure to be added.
     * @param ui       Ui of OwlMoney.
     * @param bankType Type of bank to add expenditure into.
     * @throws BankException If bank amount becomes negative after adding expenditure.
     */
    public abstract void addInExpenditure(Transaction exp, Ui ui, String bankType) throws BankException;

    /**
     * Deletes an expenditure from the current bank account.
     *
     * @param exNum Transaction number.
     * @param ui    Ui of OwlMoney.
     * @throws TransactionException If invalid transaction.
     * @throws BankException        If used on investment account.
     */
    public void deleteExpenditure(int exNum, Ui ui) throws TransactionException, BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Edits expenditure in the current bank account.
     *
     * @param expNum   Transaction number.
     * @param desc     New description.
     * @param amount   New amount.
     * @param date     New date.
     * @param category New category.
     * @param ui       Ui of OwlMoney.
     * @throws TransactionException If incorrect date format.
     * @throws BankException        If bank amount becomes negative after editing expenditure.
     */
    void editExpenditureDetails(int expNum, String desc, String amount, String date, String category, Ui ui)
            throws TransactionException, BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Edits deposit in the current bank account.
     *
     * @param expNum Transaction number.
     * @param desc   New description.
     * @param amount New amount.
     * @param date   New date.
     * @param ui     Ui of OwlMoney.
     * @throws TransactionException If incorrect date format.
     * @throws BankException        If bank amount becomes negative after editing deposit.
     */
    void editDepositDetails(int expNum, String desc, String amount, String date, Ui ui)
            throws TransactionException, BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Sets the income of the current bank.
     *
     * @param newIncome Income to set.
     * @throws BankException If used on investment account.
     */
    void setIncome(double newIncome) throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Lists expenditures from the current bank.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of expenditure to list.
     * @throws TransactionException If no expenditure is found.
     */
    void listAllExpenditure(Ui ui, int displayNum) throws TransactionException {
        throw new TransactionException("This account does not support this feature");
    }

    /**
     * Lists deposits from the current bank.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of deposits to list.
     * @throws TransactionException If no deposit is found.
     * @throws BankException        If used on investment account.
     */
    void listAllDeposit(Ui ui, int displayNum) throws TransactionException, BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Adds a new deposit to the current bank account.
     *
     * @param dep      Deposit to add.
     * @param ui       Ui of OwlMoney.
     * @param bankType Type of bank to add deposit into
     * @throws BankException If used on investment account.
     */
    abstract void addDepositTransaction(Transaction dep, Ui ui, String bankType) throws BankException;

    /**
     * Deletes a deposit from the current bank account.
     *
     * @param index Transaction number.
     * @param ui    Ui of OwlMoney.
     * @throws TransactionException If transaction is not a deposit.
     * @throws BankException        If amount becomes negative after deleting deposit.
     */
    void deleteDepositTransaction(int index, Ui ui) throws TransactionException, BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Adds a bond to the current bank account.
     *
     * @param newBond the bond object.
     * @param ui      required for printing.
     * @throws BankException If used on savings account.
     */
    void addBondToInvestmentAccount(Bond newBond, Ui ui) throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Checks if the bond exists before adding.
     *
     * @param bond the bond object.
     * @throws BankException If used on savings account.
     * @throws BondException If duplicate bond name exists.
     */
    void investmentCheckBondExist(Bond bond) throws BankException, BondException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Deletes bond from the current investment account.
     *
     * @param bondName the name of the bond to delete.
     * @param ui       required for printing.
     * @throws BankException If used on savings account.
     */
    void investmentDeleteBond(String bondName, Ui ui) throws BankException, BondException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Gets the bond object from the investment account.
     *
     * @param bondName the name of the bond to retrieve.
     * @return the bond object.
     * @throws BankException If used on savings account.
     * @throws BondException If the bond does not exist.
     */
    Bond investmentGetBond(String bondName) throws BankException, BondException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Edits the bond in the bank account.
     *
     * @param bondName the name of the bond to edit.
     * @param year     the new year of the bond.
     * @param rate     the new rate.
     * @param ui       required for printing.
     * @throws BankException If used on savings account.
     */
    void investmentEditBond(String bondName, String year, String rate, Ui ui)
            throws BankException, BondException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Lists the bonds in the bank specified bank account.
     *
     * @param displayNum the number of bonds to display.
     * @param ui         required for printing.
     * @throws BankException If used on savings account.
     * @throws BondException If there are no bonds.
     */
    void investmentListBond(int displayNum, Ui ui) throws BankException, BondException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Gets the income of the instance of the bank account.
     *
     * @return The income of the bank account.
     * @throws BankException If used on investment account.
     */
    public double getIncome() throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Updates recurring transactions to transaction list if any.
     *
     * @param ui Used for printing.
     */
    abstract void updateRecurringTransactions(Ui ui);

    /**
     * Adds a new recurring expenditure to a savings account.
     *
     * @param newExpenditure New recurring expenditure to be added.
     * @param ui Used for printing.
     * @throws BankException If used on an investment account.
     * @throws TransactionException If the recurring expenditure list is full.
     */
    void savingAddRecurringExpenditure(Transaction newExpenditure, Ui ui) throws BankException, TransactionException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Deletes a recurring expenditure from the bank.
     *
     * @param index Index of the recurring expenditure.
     * @param ui Used for printing.
     * @throws BankException If used on an investment account.
     * @throws TransactionException If there are 0 recurring expenditures or the index is out of range.
     */
    void savingDeleteRecurringExpenditure(int index, Ui ui) throws BankException, TransactionException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Edits a recurring expenditure from the bank.
     *
     * @param index Index of the recurring expenditure.
     * @param ui Used for printing.
     * @throws BankException If used on an investment account.
     * @throws TransactionException If there are 0 recurring expenditures or the index is out of range.
     */
    void savingEditRecurringExpenditure(int index, String description, String amount, String category, Ui ui)
            throws BankException, TransactionException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Lists all recurring expenditures from the bank.
     *
     * @param ui Used for printing.
     * @throws BankException If used on an investment account.
     * @throws TransactionException If there are 0 recurring expenditures.
     */
    void savingListRecurringExpenditure(Ui ui) throws BankException, TransactionException {
        throw new BankException("This account does not support this feature");
    }
}
