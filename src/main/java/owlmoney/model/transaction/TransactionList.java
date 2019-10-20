package owlmoney.model.transaction;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * TransactionList  provides a layer of abstraction for the ArrayList.
 * The ArrayList will store both expenditures and deposits.
 */

public class TransactionList {

    private ArrayList<Transaction> expLists;
    private static final int ONE_INDEX = 1;
    private static final String TRANSTYPE = "transaction";
    private static final String ITEMTYPE = "item";
    private static final boolean ISMULTIPLE = true;
    private static final boolean ISSINGLE = false;

    /**
     * Creates an instance of Transaction list that contains an ArrayList of expenditures and deposits.
     */
    public TransactionList() {
        expLists = new ArrayList<Transaction>();
    }

    /**
     * Lists the expenditures in the current bank account.
     *
     * @param ui         required for printing.
     * @param displayNum Number of expenditures to list.
     * @throws TransactionException If no expenditure is found or no expenditure is in the list.
     */
    public void listExpenditure(Ui ui, int displayNum) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        } else {
            int counter = displayNum;
            boolean expenditureExist = false;
            for (int i = expLists.size() - ONE_INDEX; i >= 0; i--) {
                if (expLists.get(i).getSpent()) {
                    printOneHeader(counter, displayNum, ui);
                    printOneTransaction((i + ONE_INDEX), expLists.get(i), ISMULTIPLE, ui);
                    counter--;
                    expenditureExist = true;
                }
                if (counter <= 0 || i == 0) {
                    ui.printDivider();
                }
                if (counter <= 0) {
                    break;
                }
            }
            if (!expenditureExist) {
                throw new TransactionException("No expenditures found");
            }
        }
    }

    /**
     * Lists the deposits in the current bank account.
     *
     * @param ui         required for printing.
     * @param displayNum Number of deposits to list.
     * @throws TransactionException If no deposit is found.
     */
    public void listDeposit(Ui ui, int displayNum) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        } else {
            int counter = displayNum;
            boolean depositExist = false;
            for (int i = expLists.size() - ONE_INDEX; i >= 0; i--) {
                if (!expLists.get(i).getSpent()) {
                    printOneHeader(counter, displayNum, ui);
                    printOneTransaction((i + ONE_INDEX), expLists.get(i), ISMULTIPLE, ui);
                    counter--;
                    depositExist = true;
                }
                if (counter <= 0 || i == 0) {
                    ui.printDivider();
                }
                if (counter <= 0) {
                    break;
                }
            }
            if (!depositExist) {
                throw new TransactionException("No deposits found");
            }
        }
    }

    /**
     * Adds an expenditure to the TransactionList.
     *
     * @param exp an instance of an expenditure.
     * @param ui  required for printing.
     */
    public void addExpenditureToList(Transaction exp, Ui ui, String type) {
        expLists.add(exp);
        if (!"bond".equals(type)) {
            ui.printMessage("Added expenditure with the following details:");
            printOneTransaction(ONE_INDEX, exp, ISSINGLE, ui);
        }
    }

    /**
     * Adds a deposit to the TransactionList.
     *
     * @param dep an instance of an deposit.
     * @param ui  required for printing.
     */
    public void addDepositToList(Transaction dep, Ui ui, String bankType) {
        expLists.add(dep);
        if ("bank".equals(bankType) || "savings transfer".equals(bankType) ||
                "investment transfer".equals(bankType)) {
            ui.printMessage("Added deposit with the following details:");
            printOneTransaction(ONE_INDEX, dep, ISSINGLE, ui);
        }
    }

    /**
     * Deletes an expenditure to the TransactionList.
     *
     * @param index index of the expenditure in the TransactionList.
     * @param ui    required for printing.
     * @throws TransactionException If invalid transaction.
     */
    public double deleteExpenditureFromList(int index, Ui ui) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if (!expLists.get(index - 1).getSpent()) {
                throw new TransactionException("The transaction is a deposit");
            } else {
                Transaction temp = expLists.get(index - ONE_INDEX);
                expLists.remove(index - ONE_INDEX);
                ui.printMessage("Details of deleted Expenditure:");
                printOneTransaction(ONE_INDEX, temp, ISSINGLE, ui);
                return temp.getAmount();
            }
        } else {
            throw new TransactionException("Index is out of transaction list range");
        }
    }

    /**
     * Edits the specific expenditure in the list.
     *
     * @param expNum   Transaction number of the expenditure.
     * @param desc     New description of the expenditure.
     * @param amount   New amount of the expenditure.
     * @param date     New date of the expenditure.
     * @param category New category of the expenditure.
     * @param ui       required for printing.
     * @return New amount of the expenditure.
     * @throws TransactionException If incorrect date format.
     */
    public double editEx(int expNum, String desc, String amount, String date, String category, Ui ui)
            throws TransactionException {
        if (!(desc.isBlank() || desc.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setDescription(desc);
        }
        if (!(amount.isBlank() || amount.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setAmount(Double.parseDouble(amount));
        }
        if (!(date.isBlank() || date.isEmpty())) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            try {
                expLists.get(expNum - ONE_INDEX).setDate(temp.parse(date));
            } catch (ParseException e) {
                //check handled in ParseEditExpenditure
                throw new TransactionException(e.toString());
            }
        }
        if (!(category.isBlank() || category.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setCategory(category);
        }
        ui.printMessage("Edited details of the specified expenditure:");
        printOneTransaction(ONE_INDEX, expLists.get(expNum - ONE_INDEX), ISSINGLE, ui);
        return expLists.get(expNum - ONE_INDEX).getAmount();
    }

    /**
     * Edits the specific deposit in the list.
     *
     * @param expNum Transaction number of the deposit.
     * @param desc   New description of the deposit.
     * @param amount New amount of the deposit.
     * @param date   New date of the deposit.
     * @param ui     required for printing.
     * @return New amount of the deposit.
     * @throws TransactionException If incorrect date format.
     */
    public double editDep(int expNum, String desc, String amount, String date, Ui ui) throws TransactionException {
        ui.printMessage("Editing transaction...\n");
        if (!(desc.isBlank() || desc.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setDescription(desc);
        }
        if (!(amount.isBlank() || amount.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setAmount(Double.parseDouble(amount));
        }
        if (!(date.isBlank() || date.isEmpty())) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            try {
                expLists.get(expNum - ONE_INDEX).setDate(temp.parse(date));
            } catch (ParseException e) {
                //check handled in ParseEditExpenditure
                throw new TransactionException(e.toString());
            }
        }
        ui.printMessage("Edited details of the specified deposits:");
        printOneTransaction(ONE_INDEX, expLists.get(expNum - ONE_INDEX), ISSINGLE, ui);
        return expLists.get(expNum - ONE_INDEX).getAmount();
    }

    /**
     * Gets the specific expenditure amount.
     *
     * @param index Transaction number of the expenditure.
     * @return Amount of the expenditure.
     * @throws TransactionException If transaction is not an expenditure.
     */
    public double getExpenditureAmount(int index) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if (!expLists.get(index - ONE_INDEX).getSpent()) {
                throw new TransactionException("The transaction is a deposit");
            } else {
                return expLists.get(index - ONE_INDEX).getAmount();
            }
        } else {
            throw new TransactionException("Index is out of transaction list range");
        }
    }

    /**
     * Deletes the specific deposit from the current bank account.
     *
     * @param index Transaction number of the deposit.
     * @param ui    required for printing.
     * @return Amount of the deleted deposit.
     */
    public double deleteDepositFromList(int index, Ui ui) {
        Transaction temp = expLists.get(index - ONE_INDEX);
        expLists.remove(index - ONE_INDEX);
        ui.printMessage("Details of deleted deposit:");
        printOneTransaction(ONE_INDEX, temp, ISSINGLE, ui);
        return temp.getAmount();
    }

    /**
     * Gets the amount of the deposit specified.
     *
     * @param index Transaction number of the deposit.
     * @return Amount of the deposit
     * @throws TransactionException If transaction is not a deposit.
     */
    public double getDepositValue(int index) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if (expLists.get(index - ONE_INDEX).getSpent()) {
                throw new TransactionException("The transaction is not a deposit");
            } else {
                return expLists.get(index - ONE_INDEX).getAmount();
            }
        } else {
            throw new TransactionException("Index is out of transaction list range");
        }
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
     * @param counter    Represents the counter of the transaction for printing.
     * @param displayNum Represents number of transaction to list.
     * @param ui         The object use for printing.
     */
    private void printOneHeader(int counter, int displayNum, Ui ui) {
        if (counter == displayNum) {
            ui.printTransactionHeader(TRANSTYPE);
        }
    }
}
