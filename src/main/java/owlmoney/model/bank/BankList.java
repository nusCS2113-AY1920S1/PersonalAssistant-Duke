package owlmoney.model.bank;

import java.util.ArrayList;

import owlmoney.model.expenditure.Expenditure;
import owlmoney.ui.Ui;

/**
 * The BankList class that provides a layer of abstraction for the ArrayList that stores bank accounts.
 */

public class BankList {
    private ArrayList<Bank> bankLists;

    /**
     * Constructor that creates an arrayList of Banks.
     */
    public BankList() {
        bankLists = new ArrayList<Bank>();
    }

    /**
     * Gets the name of the bank account.
     *
     * @param bankListIndex The index of the bank account in the arrayList.
     * @return The name of the bank account.
     */
    //for testing in case
    public String getBankName(int bankListIndex) {
        return bankLists.get(bankListIndex).getAccountName();
    }

    /**
     * Returns the list of all bank accounts in the BankList.
     */
    public void listBanks() {
        if (bankLists.size() <= 0) {
            System.out.println("No bank account");
        } else {
            for (int i = 0; i < bankLists.size(); i++) {
                bankLists.get(i).getDescription();
            }
        }
    }

    /**
     * Adds an instance of a bank account into the BankList.
     *
     * @param newBank a new bank object.
     * @param ui      required for printing.
     */
    public void addBank(Bank newBank, Ui ui) {
        bankLists.add(newBank);
        ui.printMessage("Added new bank: ");
        ui.printMessage(newBank.getDescription());
    }

    /**
     * Deletes an instance of a bank account from the BankList.
     *
     * @param bankName name of the bank account.
     * @param ui       required for printing.
     */
    //need to SLAP
    public void deleteBank(String bankName, Ui ui) {
        if (bankLists.size() <= 0) {
            ui.printError("There are 0 bank accounts in your profile");
        } else {
            for (int i = 0; i < bankLists.size(); i++) {
                if (bankLists.get(i).getAccountName().equals(bankName)) {
                    ui.printMessage("Removing " + bankLists.get(i).getAccountName());
                    bankLists.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Adds an expenditure tied to a bank account.
     * This will store the expenditure in the ExpenditureList in the bank account.
     *
     * @param accName The Bank account name.
     * @param exp     The instance of the expenditure.
     * @param ui      Required for printing.
     */
    //need change exception class in the future for this
    public void addExpenditure(String accName, Expenditure exp, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(accName)) {
                bankLists.get(i).addInExpenditure(exp, ui);
                return;
            }
        }
        ui.printError("There are no account named :" + accName);
    }

    /**
     * Lists all bank accounts in the BankList.
     *
     * @param ui required for printing.
     */
    public void listBankAccount(Ui ui) {
        if (bankLists.size() <= 0) {
            ui.printError("There are 0 saving accounts.");
        }
        for (int i = 0; i < bankLists.size(); i++) {
            ui.printMessage((i + 1) + ".\n" + bankLists.get(i).getDescription());
        }
    }

    /**
     * Lists all expenditure tied to a bank account.
     *
     * @param bankToList The bank account name.
     * @param ui         required for printing.
     */
    public void listBankExpenditure(String bankToList, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankToList.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).listAllExpenditure(ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + bankToList);
    }

    /**
     * Deletes an expenditure from the expenditureList in the bank account.
     *
     * @param expNum         The expenditure number.
     * @param deleteFromBank The name of the bank account.
     * @param ui             required for printing.
     */
    public void deleteExp(int expNum, String deleteFromBank, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (deleteFromBank.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).deleteExpenditure(expNum, ui);
                return;
            }
        }
        ui.printError("Cannot find bank with name: " + deleteFromBank);
    }
}
