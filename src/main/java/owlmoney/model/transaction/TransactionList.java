package owlmoney.model.transaction;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;

import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Contains a list of all transactions in the bank account.
 */
public class TransactionList {

    private ArrayList<Transaction> transactionLists;
    private static final int ONE_INDEX = 1;
    private static final String TRANSTYPE = "transaction";
    private static final String ITEMTYPE = "item";
    private static final boolean ISMULTIPLE = true;
    private static final boolean ISSINGLE = false;
    private static final int MAX_LIST_SIZE = 2000;
    private static final int ISZERO = 0;
    private static final String FINDDESCRIPTION = "description";
    private static final String FINDCATEGORY = "category";
    private static final String FINDDATE = "date range";
    private static final int OBJ_DOES_NOT_EXIST = -1;


    /**
     * Creates an instance of Transaction list that contains an ArrayList of expenditures and deposits.
     */
    public TransactionList() {
        transactionLists = new ArrayList<Transaction>();
    }

    /**
     * Lists the expenditures in the current bank account.
     *
     * @param ui         required for printing.
     * @param expendituresToDisplay Number of expenditures to list.
     * @throws TransactionException If no expenditure is found or no expenditure is in the list.
     */
    public void listExpenditure(Ui ui, int expendituresToDisplay) throws TransactionException {
        if (transactionLists.size() <= ISZERO) {
            throw new TransactionException("There are no transactions in this bank account");
        } else {
            int counter = expendituresToDisplay;
            boolean expenditureExist = false;
            for (int i = transactionLists.size() - ONE_INDEX; i >= ISZERO; i--) {
                if (transactionLists.get(i).getSpent()) {
                    printOneHeader(counter, expendituresToDisplay, ui);
                    printOneTransaction((i + ONE_INDEX), transactionLists.get(i), ISMULTIPLE, ui);
                    counter--;
                    expenditureExist = true;
                }
                if (counter <= ISZERO || i == ISZERO) {
                    ui.printDivider();
                }
                if (counter <= ISZERO) {
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
     * @param depositsToDisplay Number of deposits to list.
     * @throws TransactionException If no deposit is found.
     */
    public void listDeposit(Ui ui, int depositsToDisplay) throws TransactionException {
        if (transactionLists.size() <= ISZERO) {
            throw new TransactionException("There are no transactions in this bank account");
        } else {
            int counter = depositsToDisplay;
            boolean depositExist = false;
            for (int i = transactionLists.size() - ONE_INDEX; i >= ISZERO; i--) {
                if (!transactionLists.get(i).getSpent()) {
                    printOneHeader(counter, depositsToDisplay, ui);
                    printOneTransaction((i + ONE_INDEX), transactionLists.get(i), ISMULTIPLE, ui);
                    counter--;
                    depositExist = true;
                }
                if (counter <= ISZERO || i == ISZERO) {
                    ui.printDivider();
                }
                if (counter <= ISZERO) {
                    break;
                }
            }
            if (!depositExist) {
                throw new TransactionException("No deposits found");
            }
        }
    }

    /**
     * Adds an expenditure to the TransactionList and print UI.
     *
     * @param newExpenditure an instance of an expenditure.
     * @param ui  required for printing.
     */
    public void addExpenditureToList(Transaction newExpenditure, Ui ui, String type) {
        if (transactionLists.size() >= MAX_LIST_SIZE) {
            transactionLists.remove(0);
        }
        transactionLists.add(newExpenditure);
        if (!"bonds".equals(type)) {
            ui.printMessage("Added expenditure with the following details:");
            printOneTransaction(ONE_INDEX, newExpenditure, ISSINGLE, ui);
        }
    }

    /**
     * Adds an expenditure to the TransactionList and do not print UI.
     * Called by Card transferExpUnpaidToPaid() only.
     *
     * @param exp an instance of an expenditure.
     */
    public void addExpenditureToList(Transaction exp, String type) {
        if (transactionLists.size() >= MAX_LIST_SIZE) {
            transactionLists.remove(0);
        }
        transactionLists.add(exp);
    }

    /**
     * Adds a deposit to the TransactionList.
     *
     * @param newDeposit an instance of an deposit.
     * @param ui  required for printing.
     */
    public void addDepositToList(Transaction newDeposit, Ui ui, String bankType) {
        if (transactionLists.size() >= MAX_LIST_SIZE) {
            transactionLists.remove(0);
        }
        transactionLists.add(newDeposit);
        if ("bank".equals(bankType) || "savings transfer".equals(bankType)
                || "investment transfer".equals(bankType)) {
            ui.printMessage("Added deposit with the following details:");
            printOneTransaction(ONE_INDEX, newDeposit, ISSINGLE, ui);
        }
    }

    /**
     * Deletes an expenditure to the TransactionList and print UI.
     *
     * @param index index of the expenditure in the TransactionList.
     * @param ui    required for printing.
     * @throws TransactionException If invalid transaction.
     */
    public double deleteExpenditureFromList(int index, Ui ui) throws TransactionException {
        if (transactionLists.size() <= ISZERO) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= ISZERO && (index - ONE_INDEX) < transactionLists.size()) {
            if (!transactionLists.get(index - 1).getSpent()) {
                throw new TransactionException("The transaction is a deposit");
            } else {
                Transaction temp = transactionLists.get(index - ONE_INDEX);
                transactionLists.remove(index - ONE_INDEX);
                ui.printMessage("Details of deleted Expenditure:");
                printOneTransaction(ONE_INDEX, temp, ISSINGLE, ui);
                return temp.getAmount();
            }
        } else {
            throw new TransactionException("Index is out of transaction list range");
        }
    }

    /**
     * Deletes an expenditure to the TransactionList and do not print UI.
     * Called by Card transferExpUnpaidToPaid() only.
     *
     * @param index index of the expenditure in the TransactionList.
     * @throws TransactionException If invalid transaction.
     */
    public double deleteExpenditureFromList(int index) throws TransactionException {
        if (transactionLists.size() <= ISZERO) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= ISZERO && (index - ONE_INDEX) < transactionLists.size()) {
            if (!transactionLists.get(index - 1).getSpent()) {
                throw new TransactionException("The transaction is a deposit");
            } else {
                Transaction temp = transactionLists.get(index - ONE_INDEX);
                transactionLists.remove(index - ONE_INDEX);
                return temp.getAmount();
            }
        } else {
            throw new TransactionException("Index is out of transaction list range");
        }
    }

    /**
     * Edits the specific expenditure in the list.
     *
     * @param expenditureIndex   Transaction number of the expenditure.
     * @param description     New description of the expenditure.
     * @param amount   New amount of the expenditure.
     * @param date     New date of the expenditure.
     * @param category New category of the expenditure.
     * @param ui       required for printing.
     * @return New amount of the expenditure.
     * @throws TransactionException If incorrect date format.
     */
    public double editExpenditure(
            int expenditureIndex, String description, String amount, String date, String category, Ui ui)
            throws TransactionException {
        if (!(description.isBlank() || description.isEmpty())) {
            transactionLists.get(expenditureIndex - ONE_INDEX).setDescription(description);
        }
        if (!(amount.isBlank() || amount.isEmpty())) {
            transactionLists.get(expenditureIndex - ONE_INDEX).setAmount(Double.parseDouble(amount));
        }
        if (!(date.isBlank() || date.isEmpty())) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            try {
                transactionLists.get(expenditureIndex - ONE_INDEX).setDate(temp.parse(date));
            } catch (ParseException e) {
                //check handled in ParseEditExpenditure
                throw new TransactionException(e.toString());
            }
        }
        if (!(category.isBlank() || category.isEmpty())) {
            transactionLists.get(expenditureIndex - ONE_INDEX).setCategory(category);
        }
        ui.printMessage("Edited details of the specified expenditure:");
        printOneTransaction(ONE_INDEX, transactionLists.get(expenditureIndex - ONE_INDEX), ISSINGLE, ui);
        return transactionLists.get(expenditureIndex - ONE_INDEX).getAmount();
    }

    /**
     * Edits the specific deposit in the list.
     *
     * @param depositIndex Transaction number of the deposit.
     * @param description   New description of the deposit.
     * @param amount New amount of the deposit.
     * @param date   New date of the deposit.
     * @param ui     required for printing.
     * @return New amount of the deposit.
     * @throws TransactionException If incorrect date format.
     */
    public double editDeposit(int depositIndex, String description, String amount, String date, Ui ui)
            throws TransactionException {
        ui.printMessage("Editing transaction...\n");
        if (!(description.isBlank() || description.isEmpty())) {
            transactionLists.get(depositIndex - ONE_INDEX).setDescription(description);
        }
        if (!(amount.isBlank() || amount.isEmpty())) {
            transactionLists.get(depositIndex - ONE_INDEX).setAmount(Double.parseDouble(amount));
        }
        if (!(date.isBlank() || date.isEmpty())) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            try {
                transactionLists.get(depositIndex - ONE_INDEX).setDate(temp.parse(date));
            } catch (ParseException e) {
                //check handled in ParseEditExpenditure
                throw new TransactionException(e.toString());
            }
        }
        ui.printMessage("Edited details of the specified deposits:");
        printOneTransaction(ONE_INDEX, transactionLists.get(depositIndex - ONE_INDEX), ISSINGLE, ui);
        return transactionLists.get(depositIndex - ONE_INDEX).getAmount();
    }

    /**
     * Gets the specific expenditure amount.
     *
     * @param index Transaction number of the expenditure.
     * @return Amount of the expenditure.
     * @throws TransactionException If transaction is not an expenditure.
     */
    public double getExpenditureAmount(int index) throws TransactionException {

        if (transactionLists.size() <= ISZERO) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= ISZERO && (index - ONE_INDEX) < transactionLists.size()) {
            if (!transactionLists.get(index - ONE_INDEX).getSpent()) {
                throw new TransactionException("The transaction is a deposit");
            } else {
                return transactionLists.get(index - ONE_INDEX).getAmount();
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
        Transaction temp = transactionLists.get(index - ONE_INDEX);
        transactionLists.remove(index - ONE_INDEX);
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
        if (transactionLists.size() <= ISZERO) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= ISZERO && (index - ONE_INDEX) < transactionLists.size()) {
            if (transactionLists.get(index - ONE_INDEX).getSpent()) {
                throw new TransactionException("The transaction is not a deposit");
            } else {
                return transactionLists.get(index - ONE_INDEX).getAmount();
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

    /**
     * Returns total amount spent in a particular month of the year.
     *
     * @param month Month to check total amount spent.
     * @param year  Year to check total amount spent.
     * @return Total amount spent in the particular month of the year.
     */
    public double getMonthAmountSpent(int month, int year) {
        double totalAmount = 0;
        for (int i = 0; i < transactionLists.size(); i++) {
            LocalDate date = transactionLists.get(i).getLocalDate();
            int expMonth = date.getMonthValue();
            int expYear = date.getYear();
            if (expMonth == month && expYear == year) {
                totalAmount += transactionLists.get(i).getAmount();
            }
        }
        return totalAmount;
    }

    /**
     * Returns the particular transaction month based on transaction number.
     *
     * @param expNum Transaction number to get the month of.
     * @return Transaction month.
     */
    public int getTransactionMonthByIndex(int expNum) {
        return transactionLists.get(expNum - 1).getLocalDate().getMonthValue();
    }

    /**
     * Returns the particular transaction year based on transaction number.
     *
     * @param expNum Transaction number to get the year of.
     * @return Transaction year.
     */
    public int getTransactionYearByIndex(int expNum) {
        return transactionLists.get(expNum - 1).getLocalDate().getYear();
    }

    /**
     * Returns true if expenditure list is empty.
     *
     * @return True if expenditure list is empty.
     */
    public boolean expListIsEmpty() {
        return transactionLists.isEmpty();
    }

    /**
     * Finds the transactions that matches with the keywords specified by the user.
     *
     * @param fromDate The date to search from.
     * @param toDate The date to search until.
     * @param description The description keyword to match against.
     * @param category The category keyword to match against.
     * @param ui The object required for printing.
     * @throws TransactionException If parsing of date fails.
     */
    public void findMatchingTransaction(String fromDate, String toDate,
            String description, String category, Ui ui) throws TransactionException {
        if (!(description.isBlank() || description.isEmpty())) {
            findByDescription(description, ui);
        }
        if (!(category.isBlank() || category.isEmpty())) {
            findByCategory(category, ui);
        }
        if (!(fromDate.isBlank() || fromDate.isEmpty())) {
            findByDate(fromDate, toDate, ui);
        }
    }

    /**
     * Finds the transactions that matches with the description keyword specified by the user.
     *
     * @param keyword The description keyword to match against.
     * @param ui The object required for printing.
     */
    private void findByDescription(String keyword, Ui ui) {
        String matchingKeyword = keyword.toUpperCase();
        int printCounter = 0;
        for (int i = ISZERO; i < transactionLists.size(); i++) {
            if (transactionLists.get(i).getDescription().toUpperCase().contains(matchingKeyword)) {
                printOneHeaderForFind(printCounter, FINDDESCRIPTION, ui);
                printOneTransaction((i + ONE_INDEX), transactionLists.get(i), ISMULTIPLE, ui);
                printCounter++;
            }
        }
        if (printCounter == 0) {
            ui.printMessage("No matches for the description keyword: " + keyword);
        } else {
            ui.printDivider();
        }
    }

    /**
     * Finds the transactions that matches with the category keyword specified by the user.
     *
     * @param keyword The category keyword to match against.
     * @param ui The object required for printing.
     */
    private void findByCategory(String keyword, Ui ui) {
        String matchingKeyword = keyword.toUpperCase();
        int printCounter = 0;
        for (int i = ISZERO; i < transactionLists.size(); i++) {
            if (transactionLists.get(i).getCategory().toUpperCase().contains(matchingKeyword)) {
                printOneHeaderForFind(printCounter, FINDCATEGORY, ui);
                printOneTransaction((i + ONE_INDEX), transactionLists.get(i), ISMULTIPLE, ui);
                printCounter++;
            }
        }
        if (printCounter == 0) {
            ui.printMessage("No matches for the category keyword: " + keyword);
        } else {
            ui.printDivider();
        }
    }

    /**
     * Finds the transactions that falls within the date range specified by the user.
     *
     * @param fromDate The date to search from.
     * @param toDate The date to search until.
     * @param ui The object required for printing.
     * @throws TransactionException If parsing of date fails.
     */
    private void findByDate(String fromDate, String toDate, Ui ui) throws TransactionException {
        int printCounter = 0;
        Date from;
        Date to;
        DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
        try {
            from = temp.parse(fromDate);
            to = temp.parse(toDate);
        } catch (ParseException error) {
            throw new TransactionException(error.toString());
        }
        for (int i = ISZERO; i < transactionLists.size(); i++) {
            boolean isBeforeFromDate = transactionLists.get(i).getDateInDateFormat().before(from);
            boolean isAfterToDate = transactionLists.get(i).getDateInDateFormat().after(to);
            if (!isBeforeFromDate && !isAfterToDate) {
                printOneHeaderForFind(printCounter, FINDDATE, ui);
                printOneTransaction((i + ONE_INDEX), transactionLists.get(i), ISMULTIPLE, ui);
                printCounter++;
            }
        }
        if (printCounter == 0) {
            ui.printMessage("No matches for the date range specified: " + fromDate + " to " + toDate);
        } else {
            ui.printDivider();
        }
    }

    /**
     * Prints the header to list the found transactions.
     *
     * @param counter    Represents the counter of the transaction for printing.
     * @param ui         The object use for printing.
     */
    private void printOneHeaderForFind(int counter, String findType, Ui ui) {
        if (counter == 0) {
            ui.printMessage("Find by: " + findType);
            ui.printTransactionHeader(TRANSTYPE);
        }
    }

    /**
     * Gets the size of the transactionList.
     *
     * @return the size of the transactionList.
     */
    public int getSize() {
        return transactionLists.size();
    }

    /**
     * Gets the transaction object from the transactionList by specifying the transaction index.
     *
     * @return the transaction object.
     */
    public Transaction get(int transactionIndex) {
        return transactionLists.get(transactionIndex);
    }

    /**
     * Adds an expenditure to the TransactionList.
     *
     * @param expenditure an instance of an expenditure.
     */
    public void importExpenditureToList(Transaction expenditure) {
        if (transactionLists.size() >= MAX_LIST_SIZE) {
            transactionLists.remove(0);
        }
        transactionLists.add(expenditure);
    }

    /**
     * Adds a deposit to the TransactionList.
     *
     * @param deposit an instance of an deposit.
     */
    public void importDepositToList(Transaction deposit) {
        if (transactionLists.size() >= MAX_LIST_SIZE) {
            transactionLists.remove(0);
        }
        transactionLists.add(deposit);
    }

    /**
     * Gets the expenditure id of an expenditure that matches the YearMonth date.
     *
     * @param yearMonth The date that will be used to search for expenditures.
     * @return  The expenditure id if found. Else, return -1.
     */
    public int getExpenditureIdByYearMonth(YearMonth yearMonth) {
        for (int i = 0; i < transactionLists.size(); i++) {
            LocalDate transactionDate = transactionLists.get(i).getLocalDate();
            int transactionMonth = transactionDate.getMonthValue();
            int transactionYear = transactionDate.getYear();
            int requiredMonth = yearMonth.getMonthValue();
            int requiredYear = yearMonth.getYear();
            if ((transactionMonth == requiredMonth) && (transactionYear == requiredYear)) {
                return i;
            }
        }
        return OBJ_DOES_NOT_EXIST;
    }

    /**
     * Returns an expenditure object from transaction list for the specified YearMonth date.
     *
     * @param index Transaction number of the expenditure.
     * @return an expenditure object from transaction list for the specified YearMonth date.
     */
    public Transaction getExpenditureObjectByYearMonth(int index) {
        return transactionLists.get(index);
    }
}
