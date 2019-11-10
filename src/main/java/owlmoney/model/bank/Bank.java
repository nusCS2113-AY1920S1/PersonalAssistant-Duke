package owlmoney.model.bank;

import static owlmoney.commons.log.LogsCenter.getLogger;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

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
    private static final Logger logger = getLogger(Bank.class);


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
     * @param expenditure      Expenditure to be added.
     * @param ui       Ui of OwlMoney.
     * @param bankType Type of bank to add expenditure into.
     * @throws BankException If bank amount becomes negative after adding expenditure.
     */
    public abstract void addInExpenditure(Transaction expenditure, Ui ui, String bankType) throws BankException;

    /**
     * Deletes an expenditure from the current bank account.
     *
     * @param expenditureIndex Transaction number.
     * @param ui    Ui of OwlMoney.
     * @throws TransactionException If invalid transaction.
     * @throws BankException        If used on investment account.
     */
    public void deleteExpenditure(int expenditureIndex, Ui ui) throws TransactionException, BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Edits expenditure in the current bank account.
     *
     * @param expenditureIndex   Transaction number.
     * @param description     New description.
     * @param amount   New amount.
     * @param date     New date.
     * @param category New category.
     * @param ui       Ui of OwlMoney.
     * @throws TransactionException If incorrect date format.
     * @throws BankException        If bank amount becomes negative after editing expenditure.
     */
    void editExpenditureDetails(
            int expenditureIndex, String description, String amount, String date, String category, Ui ui)
            throws TransactionException, BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Edits deposit in the current bank account.
     *
     * @param depositIndex Transaction number.
     * @param description   New description.
     * @param amount New amount.
     * @param date   New date.
     * @param ui     Ui of OwlMoney.
     * @throws TransactionException If incorrect date format.
     * @throws BankException        If bank amount becomes negative after editing deposit.
     */
    void editDepositDetails(int depositIndex, String description, String amount, String date, Ui ui)
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
     * @param expendituresToDisplay Number of expenditure to list.
     * @throws TransactionException If no expenditure is found.
     */
    void listAllExpenditure(Ui ui, int expendituresToDisplay) throws TransactionException {
        throw new TransactionException("This account does not support this feature");
    }

    /**
     * Lists deposits from the current bank.
     *
     * @param ui         Ui of OwlMoney.
     * @param depositsToDisplay Number of deposits to list.
     * @throws TransactionException If no deposit is found.
     * @throws BankException        If used on investment account.
     */
    void listAllDeposit(Ui ui, int depositsToDisplay) throws TransactionException, BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Adds a new deposit to the current bank account.
     *
     * @param deposit      Deposit to add.
     * @param ui       Ui of OwlMoney.
     * @param bankType Type of bank to add deposit into
     * @throws BankException If used on investment account.
     */
    abstract void addDepositTransaction(Transaction deposit, Ui ui, String bankType) throws BankException;

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
     * @param investmentsToDisplay the number of bonds to display.
     * @param ui         required for printing.
     * @throws BankException If used on savings account.
     * @throws BondException If there are no bonds.
     */
    void investmentListBond(int investmentsToDisplay, Ui ui) throws BankException, BondException {
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
    abstract void updateRecurringTransactions(Ui ui) throws BankException;

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

    /**
     * Finds the bonds that matches with the keywords specified by the user.
     *
     * @param bondName The bondName keyword to match against.
     * @param ui      The object required for printing.
     * @throws BankException If used on savings account.
     * @throws BondException If no bonds could be found.
     */
    public void findBondInInvestment(String bondName, Ui ui) throws BankException, BondException {
        logger.warning("This account does not support this feature");
        throw new BankException("This account does not support this feature");
    }

    /**
     * Finds the recurring expenditure that matches with the keywords specified by the user
     * for savings account.
     *
     * @param description The description keyword to match against.
     * @param category    The category keyword to match against.
     * @param ui          The object required for printing.
     * @throws BankException If used on investment account.
     */
    public void findRecurringExpenditure(String description, String category, Ui ui)
            throws BankException {
        logger.warning("This account does not support this feature");
        throw new BankException("This account does not support this feature");
    }

    /**
     * Returns expenditure amount based on the specified expenditure id.
     *
     * @param expno Expenditure id of the expenditure to be searched.
     * @return Expenditure amount based on the specified expenditure id.
     * @throws TransactionException If transaction is not an expenditure.
     */
    double getExpAmountById(int expno) throws TransactionException {
        throw new TransactionException("This account does not support this feature");
    }

    /**
     * Finds the transactions from the bank object that matches with the keywords specified by the user.
     *
     * @param fromDate The date to search from.
     * @param toDate The date to search until.
     * @param description The description keyword to match against.
     * @param category The category keyword to match against.
     * @param ui The object required for printing.
     * @throws TransactionException  If parsing of date fails.
     */
    void findTransaction(String fromDate, String toDate, String description, String category, Ui ui)
            throws TransactionException {
        transactions.findMatchingTransaction(fromDate, toDate, description, category, ui);
    }

    /**
     * Exports bond details of the bank account.
     *
     * @param prependFileName the index of the bankAccount in the bankList.
     * @throws BankException if the bank account does not support this feature.
     * @throws IOException if there are problems with loading saved data.
     */
    void exportInvestmentBondList(String prependFileName) throws BankException, IOException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Prepares transaction details of the bank account for exporting.
     *
     * @return the arrayList that is formatted nicely ready to be exported.
     */
    ArrayList<String[]> prepareExportTransactionList() {
        ArrayList<String[]> exportArrayList = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        SimpleDateFormat exportDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        exportArrayList.add(new String[]{"description","amount","date","category","spent"});

        for (int i = 0; i < transactions.getSize(); i++) {
            String description = transactions.get(i).getDescription();
            double amount = transactions.get(i).getAmount();
            String date = exportDateFormat.format(transactions.get(i).getDateInDateFormat());
            String category = transactions.get(i).getCategory();
            boolean spent = transactions.get(i).getSpent();
            String stringAmount = decimalFormat.format(amount);
            String stringSpent = String.valueOf(spent);
            exportArrayList.add(new String[] {description,stringAmount,date,category,stringSpent});
        }
        return exportArrayList;
    }

    /**
     * Prepares transaction details of the bank account for exporting.
     *
     * @throws BankException if the bank account does not support this feature.
     * @throws IOException if there are problems with loading saved data.
     */
    ArrayList<String[]> prepareExportRecurringTransactionList() throws BankException, IOException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Exports transaction details of the bank account.
     *
     * @param prependFileName the index of the bankAccount in the bankList.
     * @throws BankException if the bank account does not support this feature.
     * @throws IOException if there are problems with loading saved data.
     */
    void exportBankTransactionList(String prependFileName) throws BankException, IOException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Exports recurring transaction details of the bank account.
     *
     * @param prependFileName the index of the bankAccount in the bankList.
     * @throws BankException if the bank account does not support this feature.
     * @throws IOException if there are problems with loading saved data.
     */
    void exportBankRecurringTransactionList(String prependFileName) throws BankException, IOException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Imports bonds from the imported saved file line by line.
     *
     * @param newBond an instance of the bond, contained in 1 line in the saved file.
     * @throws BankException if the bank account does not support this feature.
     */
    void importNewBonds(Bond newBond) throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Imports deposits from the imported saved file line by line.
     *
     * @param deposit an instance of the deposit, contained in 1 line in the saved file.
     * @param bankType the type of deposit and bank type.
     * @throws BankException if the bank account does not support this feature.
     */
    void importNewDeposit(Transaction deposit, String bankType) throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Imports expenditures from the imported saved file line by line.
     *
     * @param expenditure an instance of the expenditure, contained in 1 line in the saved file.
     * @param type the type of expenditure.
     * @throws BankException if the bank account does not support this feature.     */
    void importNewExpenditure(Transaction expenditure, String type) throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Imports recurring expenditures from the imported saved file line by line.
     *
     * @param expenditure an instance of the recurring expenditure, contained in 1 line in the saved file.
     * @throws BankException if the bank account does not support this feature.
     */
    void importNewRecurringExpenditure(Transaction expenditure) throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Checks if the bond list is full.
     *
     * @return If the bond list is full.
     * @throws BankException If used on savings account.
     */
    boolean investmentIsBondListFull() throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Gets the next income date of the bank account.
     *
     * @return The next income date of the bank account.
     * @throws BankException If used on investment account.
     */
    Date getNextIncomeDate() throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Gets if transaction list contains the specified expenditure and deposit.
     *
     * @param cardId    The bill card id to look for in transaction list.
     * @param billDate  The bill card date to look for in transaction list.
     * @return True if transaction list contains the specified expenditure and deposit.
     */
    public boolean isTransactionCardBillExist(UUID cardId, YearMonth billDate) throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Gets the index of the expenditure object that is a card bill expenditure
     * with specified card id and bill date.
     *
     * @param cardId    The bill card id to look for in transaction list.
     * @param billDate  The bill card date to look for in transaction list.
     * @return Index of the expenditure object if found. If not found, return -1.
     */
    public int getCardBillExpenditureId(UUID cardId, YearMonth billDate) throws BankException {
        throw new BankException("This account does not support this feature");
    }

    /**
     * Gets the index of the deposit object that is a card bill expenditure
     * with specified card id and bill date.
     *
     * @param cardId    The bill card id to look for in transaction list.
     * @param billDate  The bill card date to look for in transaction list.
     * @return Index of the deposit object if found. If not found, return -1.
     */
    public int getCardBillDepositId(UUID cardId, YearMonth billDate) throws BankException {
        throw new BankException("This account does not support this feature");
    }
}
