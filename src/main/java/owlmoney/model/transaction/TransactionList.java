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
            throw new TransactionException("There are no expenditure in this list");
        } else {
            int counter = displayNum;
            boolean expenditureExist = false;
            for (int i = expLists.size() - ONE_INDEX; i >= 0; i--) {
                if (!"deposit".equals(expLists.get(i).getCategory())) {
                    if (counter == displayNum) {
                        ui.printExpenditureHeader(TRANSTYPE);
                    }
                    ui.printExpenditure((i + ONE_INDEX), expLists.get(i).getDescription(),
                            (expLists.get(i).checkDebitCredit() +
                                    new DecimalFormat("0.00").format(expLists.get(i).getAmount())),
                            expLists.get(i).getDate(), expLists.get(i).getCategory());
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
                throw new TransactionException("No expenditure found");
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
                if ("deposit".equals(expLists.get(i).getCategory())) {
                    if (counter == displayNum) {
                        ui.printExpenditureHeader(TRANSTYPE);
                    }
                    ui.printExpenditure((i + ONE_INDEX), expLists.get(i).getDescription(),
                            (expLists.get(i).checkDebitCredit() +
                                    new DecimalFormat("0.00").format(expLists.get(i).getAmount())),
                            expLists.get(i).getDate(), expLists.get(i).getCategory());
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
            ui.printMessage("Added expenditure shown below:");
            printOneExpenditure(exp, ui);
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
        if ("bank".equals(bankType)) {
            ui.printMessage("Added deposit with the follwing details:");
            printOneExpenditure(dep, ui);
        }
    }

    /**
     * Deletes an expenditure to the TransactionList.
     *
     * @param index index of the expenditure in the TransactionList.
     * @param ui    required for printing.
     * @throws TransactionException If invalid transaction.
     */
    //magic int used. change next time
    public double deleteExpenditureFromList(int index, Ui ui) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this account");
        }
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if (expLists.get(index - 1).getCategory().equals("deposit")) {
                throw new TransactionException("The transaction is a deposit");
            } else {
                Transaction temp = expLists.get(index - ONE_INDEX);
                expLists.remove(index - ONE_INDEX);
                ui.printMessage("Details of deleted Expenditure:");
                printOneExpenditure(temp, ui);
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
        printOneExpenditure(expLists.get(expNum - ONE_INDEX), ui);
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
        printOneExpenditure(expLists.get(expNum - ONE_INDEX), ui);
        return expLists.get(expNum - ONE_INDEX).getAmount();
    }

    /**
     * Gets the specific expenditure amount.
     *
     * @param index Transaction number of the expenditure.
     * @param ui    required for printing.
     * @return Amount of the expenditure.
     * @throws TransactionException If transaction is not an expenditure.
     */
    public double getExpenditureAmount(int index, Ui ui) throws TransactionException {
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if ("deposit".equals(expLists.get(index - ONE_INDEX).getCategory())) {
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
        printOneExpenditure(temp, ui);
        return temp.getAmount();
    }

    /**
     * Gets the amount of the deposit specified.
     *
     * @param index Transaction number of the deposit.
     * @param ui    required for printing.
     * @return Amount of the deposit
     * @throws TransactionException If transaction is not a deposit.
     */
    public double getDepositValue(int index, Ui ui) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank");
        }
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if (!"deposit".equals(expLists.get(index - ONE_INDEX).getCategory())) {
                throw new TransactionException("The transaction is not a deposit");
            } else {
                return expLists.get(index - ONE_INDEX).getAmount();
            }
        } else {
            throw new TransactionException("Index is out of transaction list range");
        }
    }

    /**
     * Prints expenditure details.
     *
     * @param expenditure The expenditure object to be printed.
     * @param ui          The object use for printing.
     */
    private void printOneExpenditure(Transaction expenditure, Ui ui) {
        ui.printExpenditureHeader(ITEMTYPE);
        ui.printExpenditure((ONE_INDEX), expenditure.getDescription(),
                (expenditure.checkDebitCredit() + new DecimalFormat("0.00").
                        format(expenditure.getAmount())), expenditure.getDate(), expenditure.getCategory());
        ui.printDivider();
    }
}
