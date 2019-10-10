package owlmoney.model.transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;

import owlmoney.ui.Ui;

/**
 * The ExpenditureList class that provides a layer of abstraction for the ArrayList that stores expenditures.
 */

public class TransactionList {

    private ArrayList<Transaction> expLists;

    /**
     * Constructor that creates an arrayList of expenditures.
     */
    public TransactionList() {
        expLists = new ArrayList<Transaction>();
    }
    /*
    /**
     * Lists the expenditure in the expenditureList.
     *
     * @param ui required for printing.
     *//*
    public void listTransaction(Ui ui) {
        if (expLists.size() <= 0) {
            ui.printError("There are no transactions");
        } else {
            for (int i = 0; i < expLists.size(); i++) {
                ui.printMessage((i + 1) + ":\n" + expLists.get(i).getDetails() + "\n");
            }
        }
    }*/

    /**
     * Lists the expenditures in the current bank account.
     *
     * @param ui required for printing.
     * @param displayNum Number of expenditures to list.
     */
    public void listExpenditure(Ui ui, int displayNum) {
        if (expLists.size() <= 0) {
            ui.printError("There are no transactions");
        } else {
            int counter = displayNum;
            Stack<Transaction> displayStack = new Stack<>();
            for (int i = expLists.size() - 1; i >= 0; i--) {
                if (!"deposit".equals(expLists.get(i).getCategory())) {
                    displayStack.push(expLists.get(i));
                    ui.printMessage((i + 1) + ":\n" + expLists.get(i).getDetails() + "\n");
                    counter--;
                }
                if (counter <= 0) {
                    break;
                }
            }
            if (displayStack.isEmpty()) {
                ui.printError("No expenditure found");
            } else {
                while (!displayStack.isEmpty()) {
                    displayStack.pop().getDetails();
                }
            }
        }
    }

    /**
     * Lists the deposits in the current bank account.
     *
     * @param ui required for printing.
     * @param displayNum Number of deposits to list.
     */
    public void listDeposit(Ui ui, int displayNum) {
        if (expLists.size() <= 0) {
            ui.printError("There are no transactions");
        } else {
            int counter = displayNum;
            Stack<Transaction> displayStack = new Stack<>();
            for (int i = expLists.size() - 1; i >= 0; i++) {
                if ("deposit".equals(expLists.get(i).getCategory())) {
                    displayStack.push(expLists.get(i));
                    counter--;
                }
                if (counter <= 0) {
                    break;
                }
            }
            if (displayStack.isEmpty()) {
                ui.printError("No deposit found");
            } else {
                while (!displayStack.isEmpty()) {
                    displayStack.pop().getDetails();
                }
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
    public double deleteExpenditureFromList(int index, Ui ui) {
        if (expLists.size() <= 0) {
            ui.printError("There are no transactions in this bank");
            return 0;
        }
        if ((index - 1) >= 0 && (index - 1) < expLists.size()) {
            if (expLists.get(index - 1).getCategory().equals("deposit")) {
                ui.printError("The transaction is a deposit");
                return 0;
            } else {
                Transaction temp = expLists.get(index - 1);
                expLists.remove(index - 1);
                ui.printMessage("Expenditure deleted:\n" + temp.getDetails());
                return temp.getAmount();
            }
        } else {
            ui.printError("Out of transaction list range");
            return 0;
        }
    }

    /**
     * Edits the specific expenditure in the list.
     *
     * @param expNum Transaction number of the expenditure.
     * @param desc New description of the expenditure.
     * @param amount New amount of the expenditure.
     * @param date New date of the expenditure.
     * @param category New category of the expenditure.
     * @param ui required for printing.
     * @return New amount of the expenditure.
     */
    public double editEx(int expNum, String desc, String amount, String date, String category, Ui ui) {
        ui.printMessage("Editing transaction...\n");
        if (!(desc.isBlank() || desc.isEmpty())) {
            expLists.get(expNum - 1).setDescription(desc);
        }
        if (!(amount.isBlank() || amount.isEmpty())) {
            expLists.get(expNum - 1).setAmount(Double.parseDouble(amount));
        }
        if (!(date.isBlank() || date.isEmpty())) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            try {
                expLists.get(expNum - 1).setDate(temp.parse(date));
            } catch (ParseException e) {
                //check handled in ParseEditExpenditure
            }
        }
        if (!(category.isBlank() || category.isEmpty())) {
            expLists.get(expNum - 1).setCategory(category);
        }
        ui.printMessage("Edited details:\n" + expLists.get(expNum - 1).getDetails());
        return expLists.get(expNum - 1).getAmount();
    }

    /**
     * Edits the specific deposit in the list.
     *
     * @param expNum Transaction number of the deposit.
     * @param desc New description of the deposit.
     * @param amount New amount of the deposit.
     * @param date New date of the deposit.
     * @param ui required for printing.
     * @return New amount of the deposit.
     */
    public double editDep(int expNum, String desc, String amount, String date, Ui ui) {
        ui.printMessage("Editing transaction...\n");
        if (!(desc.isBlank() || desc.isEmpty())) {
            expLists.get(expNum - 1).setDescription(desc);
        }
        if (!(amount.isBlank() || amount.isEmpty())) {
            expLists.get(expNum - 1).setAmount(Double.parseDouble(amount));
        }
        if (!(date.isBlank() || date.isEmpty())) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            try {
                expLists.get(expNum - 1).setDate(temp.parse(date));
            } catch (ParseException e) {
                //check handled in ParseEditExpenditure
            }
        }
        ui.printMessage("Edited details:\n" + expLists.get(expNum - 1).getDetails());
        return expLists.get(expNum - 1).getAmount();
    }

    /**
     * Gets the specific expenditure amount.
     *
     * @param index Transaction number of the expenditure.
     * @param ui required for printing.
     * @return Amount of the expenditure.
     */
    public double getExpenditureAmount(int index, Ui ui) {
        if ((index - 1) >= 0 && (index - 1) < expLists.size()) {
            if ("deposit".equals(expLists.get(index - 1).getCategory())) {
                ui.printError("The transaction is a deposit");
                return -1.0;
            } else {
                return expLists.get(index - 1).getAmount();
            }
        } else {
            ui.printError("Out of transaction list range");
            return -1.0;
        }
    }

    /**
     * Deletes the specific deposit from the current bank account.
     *
     * @param index Transaction number of the deposit.
     * @param ui required for printing.
     * @return Amount of the deleted deposit.
     */
    public double deleteDepositFromList(int index, Ui ui) {
        Transaction temp = expLists.get(index - 1);
        expLists.remove(index - 1);
        ui.printMessage("Deposit deleted:\n" + temp.getDetails());
        return temp.getAmount();
    }

    /**
     * Gets the amount of the deposit specified.
     *
     * @param index Transaction number of the deposit.
     * @param ui required for printing.
     * @return Amount of the deposit
     */
    public double getTransactionValue(int index, Ui ui) {
        if (expLists.size() <= 0) {
            ui.printError("There are no transactions in this bank");
            return 0;
        }
        if ((index - 1) >= 0 && (index - 1) < expLists.size()) {
            if (!"deposit".equals(expLists.get(index - 1).getCategory())) {
                ui.printError("The transaction is not a deposit");
                return -1.0;
            } else {
                return expLists.get(index - 1).getAmount();
            }
        } else {
            ui.printError("Out of transaction list range");
            return -1.0;
        }
    }
}
