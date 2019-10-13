package owlmoney.model.bank;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.BondList;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

public class Investment extends Bank {

    private static final String INVESTMENT = "investment";
    BondList bonds;

    /**
     * Creates an instance of an investment account.
     *
     * @param name          The name of the bank account.
     * @param currentAmount The current amount of money in  the bank account.
     */
    public Investment(String name, double currentAmount) {
        super(name, currentAmount);
        this.type = INVESTMENT;
        this.transactions = new TransactionList();
        this.bonds = new BondList();
    }

    /**
     * Adds an expenditure tied to this instance of the bank account.
     *
     * @param exp an instance of expenditure.
     * @param ui  required for printing.
     */
    @Override
    public void addInExpenditure(Transaction exp, Ui ui) throws BankException {
        if (exp.getAmount() > this.getCurrentAmount()) {
            throw new BankException("Bank account cannot have a negative amount");
        } else {
            transactions.addExpenditureToList(exp, ui);
            deductFromAmount(exp.getAmount());
        }
    }

    /**
     * Deletes an expenditure tied to this bank account.
     *
     * @param exId The id of the expenditure in ExpenditureList.
     * @param ui   required for printing.
     */
    @Override
    public void deleteExpenditure(int exId, Ui ui) throws TransactionException {
        addToAmount(transactions.deleteExpenditureFromList(exId, ui));
    }

    /**
     * Adds a bond to this investment account.
     *
     * @param bond the bond object.
     * @param ui   required for printing.
     */
    @Override
    void addBondToInvestmentAccount(Bond bond, Ui ui) {
        bonds.addBondToList(bond, ui);
    }

    /**
     * Checks if bond exists in the bondList.
     *
     * @param bond the bond object.
     * @return result of whether the bond exists.
     */
    @Override
    public void checkBondExist(Bond bond) throws BondException {
        bonds.bondExist(bond);
    }
}
