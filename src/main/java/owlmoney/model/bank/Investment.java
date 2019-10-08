package owlmoney.model.bank;

import owlmoney.model.bond.BondList;
import owlmoney.model.expenditure.Expenditure;
import owlmoney.model.expenditure.ExpenditureList;
import owlmoney.ui.Ui;

/**
 * An investment account class that extends a normal bank account.
 */

public class Investment extends Bank {

    private String type;
    private ExpenditureList expenditures;
    private BondList bonds;
    private static final String INVESTMENT = "investment";

    /**
     * Creates an instance of an investment account.
     *
     * @param name          The name of the bank account.
     * @param currentAmount The current amount of money in  the bank account.
     */
    public Investment(String name, double currentAmount) {
        super(name, currentAmount);
        type = INVESTMENT;
        expenditures = new ExpenditureList();
        bonds = new BondList();
    }

    /**
     * Gets the type of the bank account.
     *
     * @return the type of the bank account.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Adds an expenditure tied to this instance of the bank account.
     *
     * @param exp an instance of expenditure.
     * @param ui  required for printing.
     */
    @Override
    public void addInExpenditure(Expenditure exp, Ui ui) {
        expenditures.addToList(exp, ui);
    }

    /**
     * Lists all expenditure tied to this bank account.
     *
     * @param ui required for printing.
     */
    @Override
    public void listAllExpenditure(Ui ui) {
        expenditures.listExpenditure(ui);
    }

    /**
     * Deletes an expenditure tied to this bank account.
     *
     * @param exId The id of the expenditure in ExpenditureList.
     * @param ui   required for printing.
     */
    @Override
    public void deleteExpenditure(int exId, Ui ui) {
        expenditures.deleteFromList(exId, ui);
    }

    public void addBond() {

    }

    public void deleteBond() {

    }
}
