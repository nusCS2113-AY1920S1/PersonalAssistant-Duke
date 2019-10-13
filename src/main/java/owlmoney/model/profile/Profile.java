package owlmoney.model.profile;

import owlmoney.model.bank.Bank;
import owlmoney.model.bank.BankList;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.exception.TransactionException;
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
    public void addNewBank(Bank newBank, Ui ui) throws BankException {
        bankList.addBank(newBank, ui);
    }

    /**
     * Deletes a bank account from the BankList.
     *
     * @param bankName name of the Bank account.
     * @param ui       required for printing.
     */
    public void deleteBank(String bankName, String bankType, Ui ui) throws BankException {
        bankList.deleteBank(bankName, bankType, ui);
    }

    /**
     * Lists all the bank accounts in the BankList.
     *
     * @param ui required for printing.
     */
    public void listBanks(String bankType, Ui ui) throws BankException {
        bankList.listBankAccount(bankType, ui);
    }

    /**
     * Adds a new expenditure tied to a specific bank account.
     *
     * @param accName The name of the bank account.
     * @param exp     An expenditure object.
     * @param ui      required for printing.
     */
    public void addNewExpenditure(String accName, Transaction exp, Ui ui) throws BankException {
        bankList.addExpenditure(accName, exp, ui);
    }

    /**
     * Deletes an expenditure tied to a specific bank account.
     *
     * @param expIndex The index of the expenditure in the expenditureList tied to a specific bank account.
     * @param bankName The name of the bank account.
     * @param ui       required for printing.
     */
    public void deleteExpenditure(int expIndex, String bankName, Ui ui) throws BankException, TransactionException {
        bankList.deleteExp(expIndex, bankName, ui);
    }

    /**
     * Lists deposits from a specific bank account.
     *
     * @param listedBank Bank account to list from.
     * @param ui         required for printing.
     * @param displayNum Number of deposits to list.
     */
    public void listDeposit(String listedBank, Ui ui, int displayNum) throws BankException, TransactionException {
        bankList.listBankDeposit(listedBank, ui, displayNum);
    }

    /**
     * Lists expenditure from a specific bank account.
     *
     * @param listedBank Bank account to list from.
     * @param ui         required for printing.
     * @param displayNum Number of expenditure to list.
     */
    public void listExpenditure(String listedBank, Ui ui, int displayNum) throws BankException, TransactionException {
        bankList.listBankExpenditure(listedBank, ui, displayNum);
    }

    /**
     * Edits a specific expenditure from a specific bank account.
     *
     * @param expNum       Transaction number of the expenditure.
     * @param editFromBank Bank account of expenditure.
     * @param desc         New description of expenditure.
     * @param amount       New amount of expenditure.
     * @param date         New date of expenditure.
     * @param category     New category of expenditure.
     * @param ui           required for printing.
     */
    public void editExpenditure(int expNum, String editFromBank, String desc, String amount, String date,
            String category, Ui ui) throws BankException, TransactionException {
        bankList.editExp(expNum, editFromBank, desc, amount, date, category, ui);
    }

    /**
     * Edits a specific savings account.
     *
     * @param name    Name of savings account to be edited.
     * @param newName New name of the savings account.
     * @param amount  New amount of the savings account.
     * @param income  New income of the saving account.
     * @param ui      required for printing.
     */
    public void editSavingsAccount(String name, String newName, String amount, String income, Ui ui) {
        bankList.editSavings(name, newName, amount, income, ui);
    }

    /**
     * Edits a specific investment account.
     *
     * @param name    Name of investment account to be edited.
     * @param newName New name of the investment account.
     * @param amount  New amount of the investment account.
     * @param ui      required for printing.
     */
    public void editInvestmentAccount(String name, String newName, String amount, Ui ui) {
        bankList.editInvestment(name, newName, amount, ui);
    }

    /**
     * Deletes a specific deposit from a specific bank account.
     *
     * @param depIndex Transaction number of the deposit.
     * @param bankName Bank name of the deposit.
     * @param ui       required for printing.
     */
    public void deleteDeposit(int depIndex, String bankName, Ui ui) throws BankException, TransactionException {
        bankList.deleteDeposit(bankName, depIndex, ui);
    }

    /**
     * Adds a new deposit to a specific bank account.
     *
     * @param accName Bank account name.
     * @param dep     Deposit to be added.
     * @param ui      required for printing.
     */
    public void addNewDeposit(String accName, Transaction dep, Ui ui) throws BankException {
        bankList.addDeposit(accName, dep, ui);
    }

    /**
     * Edits a specific deposit from a specific bank account.
     *
     * @param expNum       Transaction number of the deposit.
     * @param editFromBank Bank account of deposit.
     * @param desc         New description of deposit.
     * @param amount       New amount of deposit.
     * @param date         New date of deposit.
     * @param ui           required for deposit.
     */
    public void editDeposit(int expNum, String editFromBank, String desc, String amount, String date, Ui ui)
            throws BankException, TransactionException {
        bankList.editDep(expNum, editFromBank, desc, amount, date, ui);
    }

    /**
     * Adds bond to a specific bank account.
     * @param accName the name of the bank account.
     * @param newBond the bond object.
     * @param ui      required for printing.
     */
    public void addNewBond(String accName, Bond newBond, Ui ui) throws BankException {
        bankList.addBond(accName, newBond, ui);
    }

    /**
     * Checks if the bond exists before adding.
     *
     * @param accName the bank account name.
     * @param bond the bond object.
     */
    public void isBondUnique(String accName, Bond bond) throws BankException, BondException {
        bankList.isBondExist(accName, bond);
    }


}
