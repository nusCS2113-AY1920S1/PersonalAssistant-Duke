package owlmoney.model.transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * The ExpenditureList class that provides a layer of abstraction for the ArrayList.
 * The ArrayList will store both expenditures and deposits
 */

public class TransactionList {

    private ArrayList<Transaction> expLists;
    private static final int ONE_INDEX = 1;

    /**
     * Creates an instance of Transaction list that contains an ArrayList of expenditures.
     */
    public TransactionList() {
        expLists = new ArrayList<Transaction>();
    }

    /**
     * Lists the expenditures in the current bank account.
     *
     * @param ui         required for printing.
     * @param displayNum Number of expenditures to list.
     */
    public void listExpenditure(Ui ui, int displayNum) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this list");
        } else {
            int counter = displayNum;
            boolean expenditureExist = false;
            for (int i = expLists.size() - ONE_INDEX; i >= 0; i--) {
                if (!"deposit".equals(expLists.get(i).getCategory())) {
                    ui.printMessage(i + 1 + ":\n" + expLists.get(i).getDetails() + "\n");
                    counter--;
                    expenditureExist = true;
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
     */
    public void listDeposit(Ui ui, int displayNum) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        } else {
            int counter = displayNum;
            boolean depositExist = false;
            for (int i = expLists.size() - ONE_INDEX; i >= 0; i++) {
                if ("deposit".equals(expLists.get(i).getCategory())) {
                    ui.printMessage(i + ":\n" + expLists.get(i).getDetails() + "\n");
                    counter--;
                    depositExist = true;
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
     * Adds an expenditure to the transactionList.
     *
     * @param exp an instance of an expenditure.
     * @param ui  required for printing.
     */
    public void addExpenditureToList(Transaction exp, Ui ui) {
        expLists.add(exp);
        ui.printMessage("Added expenditure:\n" + exp.getDetails());
    }

    /**
     * Adds an deposit to the transactionList.
     *
     * @param dep an instance of an deposit.
     * @param ui  required for printing.
     */
    public void addDepositToList(Transaction dep, Ui ui) {
        expLists.add(dep);
        ui.printMessage("Added deposit:\n" + dep.getDetails());
    }

    /**
     * Deletes an expenditure to the expenditureList.
     *
     * @param index index of the expenditure in the expenditureList.
     * @param ui    required for printing.
     */
    //magic int used. change next time
    public double deleteExpenditureFromList(int index, Ui ui) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank");
        }
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if (expLists.get(index - 1).getCategory().equals("deposit")) {
                throw new TransactionException("The transaction is a deposit");
            } else {
                Transaction temp = expLists.get(index - ONE_INDEX);
                expLists.remove(index - ONE_INDEX);
                ui.printMessage("Expenditure deleted:\n" + temp.getDetails());
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
     */
    public double editEx(int expNum, String desc, String amount, String date, String category, Ui ui)
            throws TransactionException {
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
        if (!(category.isBlank() || category.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setCategory(category);
        }
        ui.printMessage("Edited details:\n" + expLists.get(expNum - ONE_INDEX).getDetails());
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
        ui.printMessage("Edited details:\n" + expLists.get(expNum - 1).getDetails());
        return expLists.get(expNum - ONE_INDEX).getAmount();
    }

    /**
     * Gets the specific expenditure amount.
     *
     * @param index Transaction number of the expenditure.
     * @param ui    required for printing.
     * @return Amount of the expenditure.
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
        ui.printMessage("Deposit deleted:\n" + temp.getDetails());
        return temp.getAmount();
    }

    /**
     * Gets the amount of the deposit specified.
     *
     * @param index Transaction number of the deposit.
     * @param ui    required for printing.
     * @return Amount of the deposit
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
}
