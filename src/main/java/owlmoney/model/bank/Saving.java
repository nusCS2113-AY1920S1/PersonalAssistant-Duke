package owlmoney.model.bank;

import java.text.DecimalFormat;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Savings account class that extends a normal bank account.
 */

public class Saving extends Bank {

    private double income;
    private static final String SAVING = "saving";

    /**
     * Creates an instance of a savings account.
     *
     * @param name          The name of the bank account.
     * @param currentAmount The current amount of money in  the bank account.
     * @param income        The amount of money that is credited monthly into the account.
     */
    public Saving(String name, double currentAmount, double income) {
        super(name, currentAmount);
        this.income = income;
        this.type = SAVING;
        this.transactions = new TransactionList();
    }

    /**
     * Gets the income of the bank accounts.
     *
     * @return the income of the bank account which includes income and type.
     */
    @Override
    public double getIncome() {
        return income;
    }

    /**
     * Gets the description of the bank accounts.
     *
     * @return the description of the bank account which includes income and type.
     */
    @Override
    public String getDescription() {
        return super.getDescription() + "\nIncome: $" + new DecimalFormat("0.00").format(getIncome());
    }

    /**
     * Adds an expenditure tied to this instance of the bank account.
     *
     * @param exp      an instance of expenditure.
     * @param ui       required for printing.
     * @param bankType Type of bank to add expenditure into.
     * @throws BankException If bank account becomes negative after adding expenditure.
     */
    @Override
    public void addInExpenditure(Transaction exp, Ui ui, String bankType) throws BankException {
        if (!"bank".equals(bankType)) {
            throw new BankException("Bonds cannot be added to this account");
        }
        if (exp.getAmount() > this.getCurrentAmount()) {
            throw new BankException("Bank account cannot have a negative amount");
        } else {
            transactions.addExpenditureToList(exp, ui, bankType);
            deductFromAmount(exp.getAmount());
        }
    }

    /**
     * Lists the deposits in the current bank account.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of deposits to list.
     * @throws TransactionException If no deposit is found.
     */
    @Override
    void listAllDeposit(Ui ui, int displayNum) throws TransactionException {
        transactions.listDeposit(ui, displayNum);
    }

    /**
     * Lists the expenditures in the current bank account.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of expenditure to list.
     * @throws TransactionException If no expenditure is found.
     */
    @Override
    void listAllExpenditure(Ui ui, int displayNum) throws TransactionException {
        transactions.listExpenditure(ui, displayNum);
    }

    /**
     * Deletes an expenditure tied to this bank account.
     *
     * @param exId The id of the expenditure in ExpenditureList.
     * @param ui   required for printing.
     * @throws TransactionException If invalid transaction.
     */
    @Override
    public void deleteExpenditure(int exId, Ui ui) throws TransactionException {
        addToAmount(transactions.deleteExpenditureFromList(exId, ui));
    }

    /**
     * Sets a new income of the current bank account.
     *
     * @param newIncome Income to set.
     */
    @Override
    void setIncome(double newIncome) {
        this.income = newIncome;
    }

    /**
     * Edits the expenditure details from the current bank account.
     *
     * @param expNum   Transaction number.
     * @param desc     New description.
     * @param amount   New amount.
     * @param date     New date.
     * @param category New category.
     * @param ui       Ui of OwlMoney.
     * @throws TransactionException If incorrect date format.
     * @throws BankException        If amount is negative after editing expenditure.
     */
    @Override
    void editExpenditureDetails(int expNum, String desc, String amount, String date, String category, Ui ui)
            throws TransactionException, BankException {
        if (!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + transactions.getExpenditureAmount(expNum) < Double.parseDouble(amount)) {
            throw new BankException("Bank account cannot have a negative amount");
        }
        double oldAmount = transactions.getExpenditureAmount(expNum);
        double newAmount = transactions.editEx(expNum, desc, amount, date, category, ui);
        this.addToAmount(oldAmount);
        this.deductFromAmount(newAmount);
    }

    /**
     * Edits the deposit details from the current bank account.
     *
     * @param expNum Transaction number.
     * @param desc   New description.
     * @param amount New amount.
     * @param date   New date.
     * @param ui     Ui of OwlMoney.
     * @throws TransactionException If incorrect date format.
     * @throws BankException        If amount becomes negative after editing deposit.
     */
    @Override
    void editDepositDetails(int expNum, String desc, String amount, String date, Ui ui)
            throws TransactionException, BankException {
        if (!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + Double.parseDouble(amount) < transactions.getDepositValue(expNum)) {
            throw new BankException("Bank account cannot have a negative amount");
        }
        double oldAmount = transactions.getDepositValue(expNum);
        double newAmount = transactions.editDep(expNum, desc, amount, date, ui);
        this.addToAmount(newAmount);
        this.deductFromAmount(oldAmount);
    }

    /**
     * Adds a new deposit to the current bank account.
     *
     * @param dep      Deposit to add.
     * @param ui       Ui of OwlMoney.
     * @param bankType Type of bank to add deposit into.
     */
    @Override
    void addDepositTransaction(Transaction dep, Ui ui, String bankType) throws BankException {
        if (!"bank".equals(bankType)) {
            throw new BankException("This account does not support investment account deposits");
        }
        transactions.addDepositToList(dep, ui, bankType);
        addToAmount(dep.getAmount());
    }

    /**
     * Deletes a deposit from the current bank account.
     *
     * @param index Transaction number.
     * @param ui    Ui of OwlMoney.
     * @throws TransactionException If transaction is not a deposit.
     * @throws BankException        If amount becomes negative after editing deposit.
     */
    @Override
    void deleteDepositTransaction(int index, Ui ui) throws TransactionException, BankException {
        double depositValue = transactions.getDepositValue(index);
        if (this.getCurrentAmount() < depositValue) {
            throw new BankException("Bank account cannot have a negative amount");
        } else {
            this.deductFromAmount(transactions.deleteDepositFromList(index, ui));
        }
    }
}
