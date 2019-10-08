package owlmoney.model.transaction;

import java.util.ArrayList;

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

    /**
     * Lists the expenditure in the expenditureList.
     *
     * @param ui required for printing.
     */
    public void listTransaction(Ui ui) {
        if (expLists.size() <= 0) {
            ui.printError("There are no transactions");
        } else {
            for (int i = 0; i < expLists.size(); i++) {
                ui.printMessage((i + 1) + ":\n" + expLists.get(i).getDetails() + "\n");
            }
        }
    }

    public void listExpenditure(Ui ui, int displayNum) {
        boolean containsExpenditure = false;
        if (expLists.size() <= 0) {
            ui.printError("There are no transactions");
        } else {
            for (int i = 0; i < expLists.size(); i++) {
                if(!"deposit".equals(expLists.get(i).getCategory())) {
                    ui.printMessage((i + 1) + ":\n" + expLists.get(i).getDetails() + "\n");
                    containsExpenditure = true;
                    displayNum--;
                }
                if(displayNum <= 0) {
                    break;
                }
            }
            if (!containsExpenditure) {
                ui.printError("No expenditure found");
            }
        }
    }

    public void listDeposit(Ui ui, int displayNum) {
        boolean containsDeposit = false;
        if (expLists.size() <= 0) {
            ui.printError("There are no transactions");
        } else {
            for (int i = 0; i < expLists.size(); i++) {
                if("deposit".equals(expLists.get(i).getCategory())) {
                    ui.printMessage((i + 1) + ":\n" + expLists.get(i).getDetails() + "\n");
                    containsDeposit = true;
                    displayNum--;
                }
                if(displayNum <= 0) {
                    break;
                }
            }
        }
        if (!containsDeposit) {
            ui.printError("No deposit found");
        }
    }

    /**
     * Adds an expenditure to the expenditureList.
     *
     * @param exp an instance of an expenditure.
     * @param ui  required for printing.
     */
    public void addExpenditureToList(Transaction exp, Ui ui) {
        expLists.add(exp);
        ui.printMessage("Added expenditure:\n" + exp.getDetails());
    }

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
            if(expLists.get(index - 1).getCategory().equals("deposit")) {
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

    public double editEx(int expNum, String desc, String amount, String date, String category, Ui ui) {
        ui.printMessage("Editing transaction...\n");
        if(!(desc.isBlank() || desc.isEmpty())) {
            expLists.get(expNum - 1).setDescription(desc);
        }
        if(!(amount.isBlank() || amount.isEmpty())) {
            expLists.get(expNum - 1).setAmount(Double.parseDouble(amount));
        }
        if(!(date.isBlank() || date.isEmpty())) {
            expLists.get(expNum - 1).setDate(date);
        }
        if(!(category.isBlank() || category.isEmpty())) {
            expLists.get(expNum - 1).setCategory(category);
        }
        ui.printMessage("Edited details:\n" + expLists.get(expNum - 1).getDetails());
        return expLists.get(expNum - 1).getAmount();
    }

    public double editDep(int expNum, String desc, String amount, String date, Ui ui) {
        ui.printMessage("Editing transaction...\n");
        if(!(desc.isBlank() || desc.isEmpty())) {
            expLists.get(expNum - 1).setDescription(desc);
        }
        if(!(amount.isBlank() || amount.isEmpty())) {
            expLists.get(expNum - 1).setAmount(Double.parseDouble(amount));
        }
        if(!(date.isBlank() || date.isEmpty())) {
            expLists.get(expNum - 1).setDate(date);
        }
        ui.printMessage("Edited details:\n" + expLists.get(expNum - 1).getDetails());
        return expLists.get(expNum - 1).getAmount();
    }

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

    public double deleteDepositFromList(int index, Ui ui) {
        Transaction temp = expLists.get(index - 1);
        expLists.remove(index - 1);
        ui.printMessage("Deposit deleted:\n" + temp.getDetails());
        return temp.getAmount();
    }

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
