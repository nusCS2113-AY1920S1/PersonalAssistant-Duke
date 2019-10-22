package owlmoney.model.transaction;

import java.text.DecimalFormat;
import java.util.ArrayList;

import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Contains the details of recurring expenditures.
 */
public class RecurringExpenditureList {
    private ArrayList<Transaction> recurringExpenditures;
    private static final int MAX_LIST_SIZE = 2000;
    private static final String TRANSTYPE = "transaction";
    private static final String ITEMTYPE = "item";
    private static final boolean ISMULTIPLE = true;
    private static final boolean ISSINGLE = false;

    /**
     * Creates an instance of Recurring Expenditure list that contains an ArrayList of recurring expenditures.
     */
    public RecurringExpenditureList() {
        recurringExpenditures = new ArrayList<Transaction>();
    }

    /**
     * Adds a new recurring expenditure to the list.
     *
     * @param newExpenditure New recurring expenditure to be added.
     * @param ui Used for printing.
     * @throws TransactionException If the list hits the maximum size.
     */
    public void addRecurringExpenditure(Transaction newExpenditure, Ui ui)
            throws TransactionException {
        if (recurringExpenditures.size() >= MAX_LIST_SIZE) {
            throw new TransactionException("The list has reach a max size of " + MAX_LIST_SIZE);
        }
        recurringExpenditures.add(newExpenditure);
        ui.printMessage("Added expenditure with the following details:");
        printOneTransaction(1, newExpenditure, ISSINGLE, ui);
    }

    /**
     * Deletes a recurring expenditure from the list.
     *
     * @param index Index of the expenditure in the list.
     * @param ui Used for printing.
     * @throws TransactionException There are 0 recurring expenditures or index is out of range.
     */
    public void deleteRecurringExpenditure(int index, Ui ui) throws TransactionException {
        if (recurringExpenditures.size() <= 0) {
            throw new TransactionException("There are no recurring expenditures in this bank account");
        }
        if (!((index - 1) >= 0 && (index - 1) < recurringExpenditures.size())) {
            throw new TransactionException("Index is out of transaction list range");
        }
        Transaction temp = recurringExpenditures.get(index - 1);
        recurringExpenditures.remove(index - 1);
        ui.printMessage("Deleted expenditure with the following details:");
        printOneTransaction(1, temp, ISSINGLE, ui);
    }

    /**
     * Lists all recurring expenditures in the list.
     *
     * @param ui Used for printing.
     * @throws TransactionException If there are 0 recurring expenditures.
     */
    public void listRecurringExpenditure(Ui ui) throws TransactionException {
        if (recurringExpenditures.size() <= 0) {
            throw new TransactionException("There are no recurring expenditures in this account");
        }
        for (int i = 0; i < recurringExpenditures.size(); i++) {
            printOneHeader(ui);
            printOneTransaction((i + 1), recurringExpenditures.get(i), ISMULTIPLE, ui);
        }
        ui.printDivider();
    }

    /**
     * Edits a recurring expenditure from the list.
     *
     * @param index Index of the recurring expenditure.
     * @param description New description of the recurring expenditure.
     * @param amount New amount of the recurring expenditure.
     * @param category New category of the recurring expenditure.
     * @param ui Used for printing.
     * @throws TransactionException If there are 0 recurring expenditures or index is out of range.
     */
    public void editRecurringExpenditure(
            int index, String description, String amount, String category, Ui ui)
            throws TransactionException {
        if (recurringExpenditures.size() <= 0) {
            throw new TransactionException("There are no recurring expenditures in this account");
        }
        if (!((index - 1) >= 0 && (index - 1) < recurringExpenditures.size())) {
            throw new TransactionException("Index is out of transaction list range");
        }
        if (!description.isBlank()) {
            recurringExpenditures.get(index - 1).setDescription(description);
        }
        if (!amount.isBlank()) {
            recurringExpenditures.get(index - 1).setAmount(Double.parseDouble(amount));
        }
        if (!category.isBlank()) {
            recurringExpenditures.get(index - 1).setCategory(category);
        }
        ui.printMessage("Edited details of the specified expenditure:");
        printOneTransaction(1, recurringExpenditures.get(index - 1), ISSINGLE, ui);
    }

    /**
     * Gets the recurring expenditure in the specified index.
     *
     * @param index Index of the recurring expenditure.
     * @return The recurring expenditure.
     */
    public Transaction getRecurringExpenditure(int index) {
        return recurringExpenditures.get(index);
    }

    /**
     * Gets the total number of recurring expenditures in the list.
     *
     * @return The number of recurring expenditures in the list.
     */
    public int getListSize() {
        return recurringExpenditures.size();
    }

    /**
     * Prints transaction details.
     *
     * @param num                Represents the numbering of the transaction.
     * @param transaction        The transaction object to be printed.
     * @param isMultiplePrinting Represents whether the function will be called for printing once or multiple
     *                           time
     * @param ui                 The object use for printing.
     */
    private void printOneTransaction(int num, Transaction transaction, boolean isMultiplePrinting, Ui ui) {
        if (!isMultiplePrinting) {
            ui.printTransactionHeader(ITEMTYPE);
        }
        ui.printTransaction(num, transaction.getDescription(),
                (transaction.checkDebitCredit() + new DecimalFormat("0.00")
                        .format(transaction.getAmount())), transaction.getDate(), transaction.getCategory());
        if (!isMultiplePrinting) {
            ui.printDivider();
        }
    }

    /**
     * Prints the transaction header details once only when listing of multiple transaction.
     *
     * @param ui         The object use for printing.
     */
    private void printOneHeader(Ui ui) {
        ui.printTransactionHeader(TRANSTYPE);
    }
}
