package owlmoney.model.bank;

import owlmoney.model.expenditure.Expenditure;
import owlmoney.model.expenditure.ExpenditureList;
import owlmoney.ui.Ui;

/**
 * A savings account class that extends a normal bank account.
 */

public class Saving extends Bank {

    private String type;
    private double income;
    private ExpenditureList myExpenditure;
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
        myExpenditure = new ExpenditureList();
    }

    /**
     * Gets the description of the bank accounts.
     *
     * @return the description of the bank account which includes income and type.
     */
    @Override
    public String getDescription() {
        return super.getDescription() + "\nIncome: " + income + "\nType: " + type;
    }

    /**
     * Adds an expenditure tied to this instance of the bank account.
     *
     * @param exp an instance of expenditure.
     * @param ui  required for printing.
     */
    @Override
    public void addInExpenditure(Expenditure exp, Ui ui) {
        myExpenditure.addToList(exp, ui);
    }

    /**
     * Lists all expenditure tied to this bank account.
     *
     * @param ui required for printing.
     */
    @Override
    public void listAllExpenditure(Ui ui) {
        myExpenditure.listExpenditure(ui);
    }

    /**
     * Deletes an expenditure tied to this bank account.
     *
     * @param exId The id of the expenditure in ExpenditureList.
     * @param ui   required for printing.
     */
    @Override
    public void deleteExpenditure(int exId, Ui ui) {
        myExpenditure.deleteFromList(exId, ui);
    }
}
