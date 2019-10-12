package owlmoney.model.bank;

import java.text.DecimalFormat;

import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.ui.Ui;

/**
 * A savings account class that extends a normal bank account.
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

    private double getIncome() {
        return income;
    }

    /**
     * Gets the description of the bank accounts.
     *
     * @return the description of the bank account which includes income and type.
     */
    @Override
    public String getDescription() {
        return super.getDescription() + "\nIncome: " + new DecimalFormat("0.00").format(getIncome());
    }

    /**
     * Adds an expenditure tied to this instance of the bank account.
     *
     * @param exp an instance of expenditure.
     * @param ui  required for printing.
     */
    @Override
    public void addInExpenditure(Transaction exp, Ui ui) {
        if (exp.getAmount() > this.getCurrentAmount()) {
            ui.printError("Bank account cannot have a negative amount");
        } else {
            transactions.addExpenditureToList(exp, ui);
            deductFromAmount(exp.getAmount());
        }
    }

    /**
     * Lists the deposits in the current bank account.
     *
     * @param ui Ui of OwlMoney.
     * @param displayNum Number of deposits to list.
     */
    @Override
    void listAllDeposit(Ui ui, int displayNum) {
        transactions.listDeposit(ui, displayNum);
    }

    /**
     * Lists the expenditures in the current bank account.
     *
     * @param ui Ui of OwlMoney.
     * @param displayNum Number of expenditure to list.
     */
    @Override
    void listAllExpenditure(Ui ui, int displayNum) {
        transactions.listExpenditure(ui, displayNum);
    }

    /**
     * Deletes an expenditure tied to this bank account.
     *
     * @param exId The id of the expenditure in ExpenditureList.
     * @param ui   required for printing.
     */
    @Override
    public void deleteExpenditure(int exId, Ui ui) {
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
     * @param expNum Transaction number.
     * @param desc New description.
     * @param amount New amount.
     * @param date New date.
     * @param category New category.
     * @param ui Ui of OwlMoney.
     */
    @Override
    void editExpenditureDetails(int expNum, String desc, String amount, String date, String category, Ui ui) {
        if (transactions.getExpenditureAmount(expNum, ui) < 0) {
            return;
        }
        if (!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + transactions.getExpenditureAmount(expNum, ui) < Double.parseDouble(amount)) {
            ui.printError("Bank account cannot have a negative amount");
            return;
        }
        double oldAmount = transactions.getExpenditureAmount(expNum, ui);
        double newAmount = transactions.editEx(expNum, desc, amount, date, category, ui);
        this.addToAmount(oldAmount);
        this.deductFromAmount(newAmount);
    }

    /**
     * Edits the deposit details from the current bank account.
     *
     * @param expNum Transaction number.
     * @param desc New description.
     * @param amount New amount.
     * @param date New date.
     * @param ui Ui of OwlMoney.
     */
    @Override
    void editDepositDetails(int expNum, String desc, String amount, String date, Ui ui) {
        if (transactions.getTransactionValue(expNum, ui) < 0) {
            return;
        }
        if (!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + Double.parseDouble(amount) < transactions.getTransactionValue(expNum, ui)) {
            ui.printError("Bank account cannot have a negative amount");
            return;
        }
        double oldAmount = transactions.getTransactionValue(expNum, ui);
        double newAmount = transactions.editDep(expNum, desc, amount, date, ui);
        this.addToAmount(newAmount);
        this.deductFromAmount(oldAmount);
    }

    /**
     * Adds a new deposit to the current bank account.
     *
     * @param dep Deposit to add.
     * @param ui Ui of OwlMoney.
     */
    @Override
    void addDepositTransaction(Transaction dep, Ui ui) {
        transactions.addDepositToList(dep, ui);
        addToAmount(dep.getAmount());
    }

    /**
     * Deletes a deposit from the current bank account.
     *
     * @param index Transaction number.
     * @param ui Ui of OwlMoney.
     */
    @Override
    void deleteDepositTransaction(int index, Ui ui) {
        double depositValue = transactions.getTransactionValue(index, ui);
        if (depositValue < 0) {
            return;
        }
        if (this.getCurrentAmount() < depositValue) {
            ui.printError("Bank account cannot have a negative amount");
        } else {
            this.deductFromAmount(transactions.deleteDepositFromList(index, ui));
        }
    }
}
