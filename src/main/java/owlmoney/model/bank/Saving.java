package owlmoney.model.bank;

import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.ui.Ui;

/**
 * A savings account class that extends a normal bank account.
 */

public class Saving extends Bank {

    private double income;
    private TransactionList myTransaction;
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
        myTransaction = new TransactionList();
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
        return super.getDescription() + "\nIncome: " + getIncome() + "\nType: " + super.getType();
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
            myTransaction.addExpenditureToList(exp, ui);
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
        myTransaction.listTransaction(ui);
    }

    void listAllDeposit(Ui ui, int displayNum) {
        myTransaction.listDeposit(ui, displayNum);
    }

    void listAllExpenditure(Ui ui, int displayNum) {
        myTransaction.listExpenditure(ui, displayNum);
    }

    /**
     * Deletes an expenditure tied to this bank account.
     *
     * @param exId The id of the expenditure in ExpenditureList.
     * @param ui   required for printing.
     */
    @Override
    public void deleteExpenditure(int exId, Ui ui) {
        addToAmount(myTransaction.deleteExpenditureFromList(exId, ui));
    }

    void setIncome(double newIncome) {
        this.income = newIncome;
    }

    void editExpenditureDetails(int expNum, String desc, String amount, String date, String category, Ui ui) {
        if(myTransaction.getExpenditureAmount(expNum, ui) < 0) {
            return;
        }
        if(!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + myTransaction.getExpenditureAmount(expNum, ui) < Double.parseDouble(amount)) {
            ui.printError("Bank account cannot have a negative amount");
            return;
        }
        double oldAmount = myTransaction.getExpenditureAmount(expNum, ui);
        double newAmount = myTransaction.editEx(expNum, desc, amount, date, category, ui);
        this.addToAmount(oldAmount);
        this.deductFromAmount(newAmount);
    }

    void editDepositDetails(int expNum, String desc, String amount, String date, Ui ui) {
        if(myTransaction.getTransactionValue(expNum, ui) < 0) {
            return;
        }
        if(!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + Double.parseDouble(amount) < myTransaction.getTransactionValue(expNum, ui)) {
            ui.printError("Bank account cannot have a negative amount");
            return;
        }
        double oldAmount = myTransaction.getTransactionValue(expNum, ui);
        double newAmount = myTransaction.editDep(expNum, desc, amount, date, ui);
        this.addToAmount(newAmount);
        this.deductFromAmount(oldAmount);
    }

    void addDepositTransaction(Transaction dep, Ui ui) {
        myTransaction.addDepositToList(dep, ui);
        addToAmount(dep.getAmount());
    }

    void deleteDepositTransaction(int index, Ui ui) {
        double depositValue = myTransaction.getTransactionValue(index, ui);
        if (depositValue < 0) {
            return;
        }
        if (this.getCurrentAmount() < depositValue) {
            ui.printError("Bank account cannot have a negative amount");
        } else {
            this.deductFromAmount(myTransaction.deleteDepositFromList(index, ui));
        }
    }
}
