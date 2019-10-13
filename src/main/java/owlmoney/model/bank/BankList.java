package owlmoney.model.bank;

import java.util.ArrayList;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * The BankList class that provides a layer of abstraction for the ArrayList that stores bank accounts.
 */

public class BankList {
    private ArrayList<Bank> bankLists;
    private static final String SAVING = "saving";
    //private static final String INVESTMENT = "investment";

    /**
     * Creates a instance of BankList that contains an arrayList of Banks.
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
    public String getBankName(int bankListIndex) {
        return bankLists.get(bankListIndex).getAccountName();
    }


    /**
     * Adds an instance of a bank account into the BankList.
     *
     * @param newBank a new bank object.
     * @param ui      required for printing.
     */
    public void addBank(Bank newBank, Ui ui) throws BankException {
        if (bankAccountExists(newBank.getAccountName())) {
            throw new BankException("There is already a bank account with the name " + newBank.getAccountName());
        }
        bankLists.add(newBank);
        ui.printMessage("Added new bank: ");
        ui.printMessage(newBank.getDescription());
    }

    /**
     * Returns true if bankList is empty and false if there are banks stored in bankList.
     *
     * @return status of whether there are banks stored.
     */
    private boolean isEmpty() {
        return bankLists.isEmpty();
    }

    /**
     * Gets the size of the bankList which counts all types of accounts.
     *
     * @return size of bankList.
     */
    private int getBankListSize() {
        return bankLists.size();
    }

    /**
     * Counts the number of bank accounts of the type specified.
     *
     * @param accountType The type of bank account
     * @return the number of accounts of the specified type.
     */
    private int getNumberOfAccountType(String accountType) {
        int counter = 0;
        for (int i = 0; i < getBankListSize(); i++) {
            if (accountType.equals(bankLists.get(i).getType())) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Checks if the bank name and type that the user specified is correct.
     *
     * @param bankName name of bank account.
     * @param bankType type of bank account.
     * @return the result bankName is of bankType.
     */
    private boolean hasCorrectBankNameAndType(String bankName, String bankType) {
        for (int i = 0; i < getBankListSize(); i++) {
            if ((bankName.equals(bankLists.get(i).getAccountName()))
                    && (bankType.equals(bankLists.get(i).getType()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the bank name that the user specified exists.
     *
     * @param bankName name of bank account.
     * @return the result bankName exists.
     */
    private boolean bankAccountExists(String bankName) {
        for (int i = 0; i < getBankListSize(); i++) {
            if (bankName.equals(bankLists.get(i).getAccountName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the user passes all requirements to delete a bank account.
     *
     * @param bankName name of bank account.
     * @param bankType type of bank account.
     * @param ui       required for printing.
     * @return the result bankName is of bankType.
     */
    private boolean canPassDeleteBankRequirements(String bankName, String bankType, Ui ui) throws BankException {
        if (isEmpty()) {
            throw new BankException("There are 0 bank accounts in your profile");
        }
        if (bankType.equals(SAVING) && getNumberOfAccountType(SAVING) == 1) {
            throw new BankException("There must be at least 1 savings account");
        }
        if (!bankAccountExists(bankName)) {
            throw new BankException("There are no bank accounts with name " + bankName);
        }
        if (hasCorrectBankNameAndType(bankName, bankType)) {
            return true;
        } else {
            throw new BankException(bankName + " is not not of type: " + bankType);
        }
    }

    /**
     * Deletes an instance of a bank account from the BankList.
     *
     * @param bankName name of the bank account.
     * @param bankType type of bank account.
     * @param ui       required for printing.
     */
    public void deleteBank(String bankName, String bankType, Ui ui) throws BankException {
        if (canPassDeleteBankRequirements(bankName, bankType, ui)) {
            for (int i = 0; i < getBankListSize(); i++) {
                if (bankName.equals(bankLists.get(i).getAccountName())) {
                    ui.printMessage("Removing " + bankLists.get(i).getAccountName());
                    bankLists.remove(i);
                    break;
                }
            }
        }
    }

    /**
     * Edits the saving details.
     *
     * @param bankName Bank account to be edited.
     * @param newName  New name of bank account.
     * @param amount   New amount of bank account.
     * @param income   New income of bank account.
     * @param ui       required for printing.
     */
    public void editSavings(String bankName, String newName, String amount, String income, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(bankName)
                    && "saving".equals(bankLists.get(i).getType())) {
                ui.printMessage("Editing " + bankLists.get(i).getAccountName() + "\n");
                if (!(newName.isEmpty() || newName.isBlank())) {
                    bankLists.get(i).setAccountName(newName);
                }
                if (!(amount.isBlank() || amount.isEmpty())) {
                    bankLists.get(i).setCurrentAmount(Double.parseDouble(amount));
                }
                if (!(income.isEmpty() || income.isBlank())) {
                    bankLists.get(i).setIncome(Double.parseDouble(income));
                }
                ui.printMessage("New details of the account:\n");
                ui.printMessage(bankLists.get(i).getDescription() + "\n");
                break;
            }
        }
    }

    /**
     * Edits the investment account details.
     *
     * @param bankName Bank account to be edited.
     * @param newName  New name of bank account.
     * @param amount   New amount of bank account.
     * @param ui       required for printing.
     */
    public void editInvestment(String bankName, String newName, String amount, Ui ui) {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(bankName)
                    && "investment".equals(bankLists.get(i).getType())) {
                ui.printMessage("Editing " + bankLists.get(i).getAccountName() + "\n");
                if (!(newName.isEmpty() || newName.isBlank())) {
                    bankLists.get(i).setAccountName(newName);
                }
                if (!(amount.isBlank() || amount.isEmpty())) {
                    bankLists.get(i).setCurrentAmount(Double.parseDouble(amount));
                }
                ui.printMessage("New details of the account:\n");
                ui.printMessage(bankLists.get(i).getDescription() + "\n");
                break;
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
    public void addExpenditure(String accName, Transaction exp, Ui ui) throws BankException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(accName)) {
                bankLists.get(i).addInExpenditure(exp, ui);
                return;
            }
        }
        throw new BankException("There is no account with the name: " + accName);
    }

    /**
     * Lists all bank accounts in the BankList.
     *
     * @param ui required for printing.
     */
    public void listBankAccount(String bankType, Ui ui) throws BankException {
        if (getBankListSize() <= 0) {
            throw new BankException("There are 0 bank accounts");
        }
        for (int i = 0; i < getBankListSize(); i++) {
            if (bankType.equals(bankLists.get(i).getType())) {
                ui.printMessage((i + 1) + ".\n" + bankLists.get(i).getDescription());
            }
        }
    }

    /**
     * Lists expenditures in the bank account.
     *
     * @param bankToList The name of the bank account.
     * @param ui         required for printing.
     * @param displayNum Number of expenditures to list.
     */
    public void listBankExpenditure(String bankToList, Ui ui, int displayNum) throws TransactionException,
            BankException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankToList.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).listAllExpenditure(ui, displayNum);
                return;
            }
        }
        throw new BankException("Cannot find bank with name: " + bankToList);
    }

    /**
     * Lists deposits in the bank account.
     *
     * @param bankToList The name of the bank account.
     * @param ui         required for printing.
     * @param displayNum Number of deposits to list.
     */
    public void listBankDeposit(String bankToList, Ui ui, int displayNum) throws TransactionException,
            BankException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankToList.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).listAllDeposit(ui, displayNum);
                return;
            }
        }
        throw new BankException("Cannot find bank with name: " + bankToList);
    }

    /**
     * Deletes an expenditure from the transactionList in the bank account.
     *
     * @param expNum         The transaction number.
     * @param deleteFromBank The name of the bank account.
     * @param ui             required for printing.
     */
    public void deleteExp(int expNum, String deleteFromBank, Ui ui) throws TransactionException,
            BankException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (deleteFromBank.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).deleteExpenditure(expNum, ui);
                return;
            }
        }
        throw new BankException("Cannot find bank with name: " + deleteFromBank);
    }

    /**
     * Edits an expenditure from the transactionList in the bank account.
     *
     * @param expNum       The transaction number.
     * @param editFromBank The name of the bank account.
     * @param desc         The description of the expenditure.
     * @param amount       The amount of the expenditure.
     * @param date         The date of the expenditure.
     * @param category     The category of the expenditure.
     * @param ui           required for printing.
     */
    public void editExp(int expNum, String editFromBank, String desc, String amount, String date, String category,
            Ui ui) throws BankException, TransactionException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(editFromBank)) {
                bankLists.get(i).editExpenditureDetails(expNum, desc, amount, date, category, ui);
                return;
            }
        }
        throw new BankException("Cannot find bank with name: " + editFromBank);
    }

    /**
     * Edits a deposit from the transactionList in the bank account.
     *
     * @param expNum       The transaction number.
     * @param editFromBank The name of the bank account.
     * @param desc         The description of the deposit.
     * @param amount       The amount of the deposit.
     * @param date         The date of the deposit.
     * @param ui           required for printing.
     */
    public void editDep(int expNum, String editFromBank, String desc, String amount, String date, Ui ui)
            throws BankException, TransactionException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(editFromBank)) {
                bankLists.get(i).editDepositDetails(expNum, desc, amount, date, ui);
                return;
            }
        }
        throw new BankException("Cannot find bank with name: " + editFromBank);
    }

    /**
     * Adds a deposit tied to a bank account.
     * This will store the expenditure in the transactionList in the bank account.
     *
     * @param accName The Bank account name.
     * @param dep     The instance of the deposit.
     * @param ui      Required for printing.
     */
    public void addDeposit(String accName, Transaction dep, Ui ui) throws BankException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(accName)) {
                bankLists.get(i).addDepositTransaction(dep, ui);
                return;
            }
        }
        throw new BankException("Cannot find bank with name: " + accName);

    }

    /**
     * Deletes a deposit from the transactionList in the bank account.
     *
     * @param accName The name of the bank account.
     * @param index   The transaction number.
     * @param ui      required for printing.
     */
    public void deleteDeposit(String accName, int index, Ui ui) throws BankException, TransactionException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (bankLists.get(i).getAccountName().equals(accName)) {
                bankLists.get(i).deleteDepositTransaction(index, ui);
                return;
            }
        }
        throw new BankException("Cannot find bank with name: " + accName);
    }

    /**
     * Adds a bond to a bank account in the bankList.
     *
     * @param accName name of bank account.
     * @param bond    bond object.
     * @param ui      required for printing.
     */
    public void addBond(String accName, Bond bond, Ui ui) throws BankException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (accName.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).addBondToInvestmentAccount(bond, ui);
                return;
            }
        }
        throw new BankException("Cannot find bank with name: " + accName);
    }

    /**
     * Checks if the bond exists before adding.
     *
     * @param accName the bank account name.
     * @param bond the bond object.
     */
    public void isBondExist(String accName, Bond bond) throws BankException, BondException {
        for (int i = 0; i < bankLists.size(); i++) {
            if (accName.equals(bankLists.get(i).getAccountName())) {
                bankLists.get(i).checkBondExist(bond);
                return;
            }
        }
        throw new BankException("Cannot find bank with name: " + accName);
    }
}
