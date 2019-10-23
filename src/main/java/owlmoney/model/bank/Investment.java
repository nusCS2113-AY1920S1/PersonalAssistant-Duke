package owlmoney.model.bank;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.BondList;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.ui.Ui;

/**
 * Investment account class that extends a normal bank account.
 */
public class Investment extends Bank {

    private static final String INVESTMENT = "investment";
    private BondList bonds;

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
     * @param exp      an instance of expenditure.
     * @param ui       required for printing.
     * @param bankType Type of bank to add expenditure into.
     * @throws BankException If amount becomes negative after adding expenditure.
     */
    @Override
    public void addInExpenditure(Transaction exp, Ui ui, String bankType) throws BankException {
        if (!"bonds".equals(bankType) && !"investment transfer".equals(bankType)) {
            throw new BankException("This account does not support savings expenditures");
        }
        if (exp.getAmount() > this.getCurrentAmount()) {
            throw new BankException("Bank account cannot have a negative amount");
        } else {
            transactions.addExpenditureToList(exp, ui, bankType);
            deductFromAmount(exp.getAmount());
        }
    }

    /**
     * Adds a new deposit to the current bank account.
     *
     * @param dep      Deposit to add.
     * @param ui       Ui of OwlMoney.
     * @param bankType Type of bank to add deposit into.
     * @throws BankException If user manually adds deposit into investment account
     */
    @Override
    public void addDepositTransaction(Transaction dep, Ui ui, String bankType) throws BankException {
        if (!"bonds".equals(bankType) && !"investment transfer".equals(bankType)) {
            throw new BankException("This account does not support this feature");
        }
        transactions.addDepositToList(dep, ui, bankType);
        addToAmount(dep.getAmount());
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
     * @throws BondException If duplicate bond name exists.
     */
    @Override
    void investmentCheckBondExist(Bond bond) throws BondException {
        bonds.bondExist(bond);
    }

    /**
     * Deletes bond from the bondList in the specified investment account.
     *
     * @param bondName the name of the bond to delete.
     * @param ui       required for printing.
     * @throws BondException if the bond does not exist.
     */
    @Override
    void investmentDeleteBond(String bondName, Ui ui) throws BondException {
        bonds.removeBondFromList(bondName, ui);
    }

    /**
     * Gets the bond object from the investment account.
     *
     * @param bondName the name of the bond to retrieve.
     * @return the bond object.
     * @throws BondException if the bond does not exist.
     */
    @Override
    Bond investmentGetBond(String bondName) throws BondException {
        return bonds.getBond(bondName);
    }

    /**
     * Edits the bond in the bank account.
     *
     * @param bondName the name of the bond to edit.
     * @param year     the new year of the bond.
     * @param rate     the new rate.
     * @param ui       required for printing.
     * @throws BondException If the bond does not exist or the year is smaller than the original.
     */
    @Override
    void investmentEditBond(String bondName, String year, String rate, Ui ui) throws BondException {
        bonds.editBond(bondName, year, rate, ui);
    }

    /**
     * Lists the bonds in the bank specified bank account.
     *
     * @param displayNum the number of bonds to display.
     * @param ui         required for printing.
     * @throws BondException if there are not bonds.
     */
    @Override
    void investmentListBond(int displayNum, Ui ui) throws BondException {
        bonds.listBond(displayNum, ui);
    }

    @Override
    public void updateRecurringTransactions(Ui ui) {
        //add your code here
    }

    /**
     * Adds a bond to this investment account.
     *
     * @param bond the bond object.
     * @param ui   required for printing.
     */
    @Override
    public void findBondInInvestment(String bondName, Ui ui) throws BondException {
        bonds.findBond(bondName, ui);
    }
}
