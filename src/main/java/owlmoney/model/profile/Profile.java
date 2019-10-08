package owlmoney.model.profile;

import owlmoney.model.bank.Bank;
import owlmoney.model.bank.BankList;
import owlmoney.model.transaction.Transaction;
import owlmoney.ui.Ui;

/**
 * The profile class that stores details of the user which includes bank accounts, cards, names.
 */

public class Profile {
    private String username;
    private BankList bankList;

    /**
     * Constructor that creates a new instance of the user profile.
     *
     * @param newUserName The username that the user desires to use.
     */
    public Profile(String newUserName) {
        this.username = newUserName;
        this.bankList = new BankList();
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the profile.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Adds a new bank account into the BankList stored in this profile.
     *
     * @param newBank an instance of the new bank account.
     * @param ui      required for printing.
     */
    public void addNewBank(Bank newBank, Ui ui) {
        bankList.addBank(newBank, ui);
    }

    /**
     * Deletes a bank account from the BankList.
     *
     * @param bankName name of the Bank account.
     * @param ui       required for printing.
     */
    public void deleteBank(String bankName, Ui ui) {
        bankList.deleteBank(bankName, ui);
    }

    /**
     * Lists all the bank accounts in the BankList.
     *
     * @param ui required for printing.
     */
    public void listBanks(Ui ui) {
        bankList.listBankAccount(ui);
    }

    /**
     * Adds a new expenditure tied to a specific bank account.
     *
     * @param accName The name of the bank account.
     * @param exp     An expenditure object.
     * @param ui      required for printing.
     */
    public void addNewExpenditure(String accName, Transaction exp, Ui ui) {
        bankList.addExpenditure(accName, exp, ui);
    }

    /**
     * Deletes an expenditure tied to a specific bank account.
     *
     * @param expIndex The index of the expenditure in the expenditureList tied to a specific bank account.
     * @param bankName The name of the bank account.
     * @param ui       required for printing.
     */
    public void deleteExpenditure(int expIndex, String bankName, Ui ui) {
        bankList.deleteExp(expIndex, bankName, ui);
    }

    /**
     * Lists all expenditure tied to a bank account.
     *
     * @param listedBank The bank account name.
     * @param ui         required for printing.
     */
    public void listTransaction(String listedBank, Ui ui) {
        bankList.listBankTransaction(listedBank, ui);
    }

    public void listDeposit(String listedBank, Ui ui, int displayNum) {
        bankList.listBankDeposit(listedBank, ui, displayNum);
    }

    public void listExpenditure(String listedBank, Ui ui, int displayNum) {
        bankList.listBankExpenditure(listedBank, ui, displayNum);
    }

    public void editExpenditure(int expNum, String editFromBank, String desc, String amount, String date
            , String category, Ui ui) {
        bankList.editExp(expNum, editFromBank, desc, amount, date, category, ui);
    }

    public void editSavingsAccount(String name, String newName, String amount, String income, Ui ui) {
        bankList.editSavings(name, newName, amount, income, ui);
    }

    public void deleteDeposit(int depIndex, String bankName, Ui ui) {
        bankList.deleteDeposit(bankName, depIndex, ui);
    }

    public void addNewDeposit(String accName, Transaction exp, Ui ui) {
        bankList.addDeposit(accName, exp, ui);
    }

    public  void editDeposit(int expNum, String editFromBank, String desc, String amount, String date, Ui ui) {
        bankList.editDep(expNum, editFromBank, desc, amount, date, ui);
    }
}
