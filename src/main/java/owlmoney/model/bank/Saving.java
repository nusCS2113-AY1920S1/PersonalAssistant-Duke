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
     * Constructor that creates an instance of a savings account.
     *
     * @param name          The name of the bank account.
     * @param currentAmount The current amount of money in  the bank account.
     * @param income        The amount of money that is credited monthly into the account.
     */
    public Saving(String name, double currentAmount, double income) {
        super(name, currentAmount);
        this.income = income;
        type = SAVING;
        transactions = new TransactionList();
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
        if(exp.getAmount() > this.getCurrentAmount()) {
            ui.printError("Bank account cannot have a negative amount");
        } else {
            transactions.addExpenditureToList(exp, ui);
            deductFromAmount(exp.getAmount());
        }
    }

    /**
     * Lists all expenditure tied to this bank account.
     *
     * @param ui required for printing.
     */
    @Override
    public void listAllTransaction(Ui ui) {
        transactions.listTransaction(ui);
    }

    @Override
    void listAllDeposit(Ui ui, int displayNum) {
        transactions.listDeposit(ui, displayNum);
    }

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

    @Override
    void setIncome(double newIncome) {
        this.income = newIncome;
    }

    @Override
    void editExpenditureDetails(int expNum, String desc, String amount, String date, String category, Ui ui) {
        if(transactions.getExpenditureAmount(expNum, ui) < 0) {
            return;
        }
        if(!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + transactions.getExpenditureAmount(expNum, ui) < Double.parseDouble(amount)) {
            ui.printError("Bank account cannot have a negative amount");
            return;
        }
        double oldAmount = transactions.getExpenditureAmount(expNum, ui);
        double newAmount = transactions.editEx(expNum, desc, amount, date, category, ui);
        this.addToAmount(oldAmount);
        this.deductFromAmount(newAmount);
    }

    @Override
    void editDepositDetails(int expNum, String desc, String amount, String date, Ui ui) {
        if(transactions.getTransactionValue(expNum, ui) < 0) {
            return;
        }
        if(!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + Double.parseDouble(amount) < transactions.getTransactionValue(expNum, ui)) {
            ui.printError("Bank account cannot have a negative amount");
            return;
        }
        double oldAmount = transactions.getTransactionValue(expNum, ui);
        double newAmount = transactions.editDep(expNum, desc, amount, date, ui);
        this.addToAmount(newAmount);
        this.deductFromAmount(oldAmount);
    }

    @Override
    void addDepositTransaction(Transaction dep, Ui ui) {
        transactions.addDepositToList(dep, ui);
        addToAmount(dep.getAmount());
    }

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
