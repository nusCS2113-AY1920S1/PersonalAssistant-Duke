package owlmoney.model.bank;

import java.util.Calendar;
import java.util.Date;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.BondList;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.transaction.Deposit;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Extends a normal bank account and produce characteristics specific for investment accounts.
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
     * @param deposit  Deposit to add.
     * @param ui       Ui of OwlMoney.
     * @param bankType Type of bank to add deposit into.
     * @throws BankException If user manually adds deposit into investment account
     */
    @Override
    public void addDepositTransaction(Transaction deposit, Ui ui, String bankType) throws BankException {
        if (!"bonds".equals(bankType) && !"investment transfer".equals(bankType)) {
            throw new BankException("This account does not support this feature");
        }
        transactions.addDepositToList(deposit, ui, bankType);
        addToAmount(deposit.getAmount());
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
        Bond targetBond = bonds.getBond(bondName);
        Calendar calendar = Calendar.getInstance();
        Transaction newDeposit = createNewDeposit(bondName,targetBond.getAmount(),calendar.getTime());
        transactions.addDepositToList(newDeposit, ui, "bonds");
        addToAmount(targetBond.getAmount());
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

    /**
     * Creates a new deposit object.
     *
     * @param bondName        the name of the bond that generated this deposit from interest.
     * @param amountToDeposit the deposit amount, generated from interest.
     * @param depositDate     the date of deposit.
     * @return the deposit object.
     */
    private Transaction createNewDeposit(String bondName, double amountToDeposit, Date depositDate) {
        Transaction newDeposit = new Deposit(bondName, amountToDeposit,
                depositDate, "bonds");
        return newDeposit;
    }

    /**
     * Updates the bank amount with the interest generated from the bond and the corresponding deposit
     * record in the transaction list.
     *
     * @param bond the bond that generated the interest.
     * @param ui   required for printing.
     */
    private void addBondInterestDeposit(Bond bond, Ui ui) {
        double interestAmount = bond.getAmount() * bond.getHalfYearlyCouponRate() / 100;
        Transaction newDeposit = createNewDeposit(bond.getName(), interestAmount, bond.getNextDateToCreditInterest());
        transactions.addDepositToList(newDeposit, ui, "bonds");
        addToAmount(interestAmount);
    }

    /**
     * Calculates the next interest rate date.
     *
     * @param currentInterestDate the current interest rate to calculate from.
     * @return the next interest rate crediting date.
     */
    private Date calculateNextInterestDate(Date currentInterestDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentInterestDate);
        calendar.add(Calendar.MONTH, 6);
        return calendar.getTime();
    }

    /**
     * Removes the bond if the bond has reached its maturity date.
     *
     * @param ui         required for printing.
     * @param targetBond the target bond to delete.
     * @param endDate    the maturity date of the bond.
     */
    private void removeBondIfMature(Ui ui, Bond targetBond, Date endDate) {
        if (targetBond.getNextDateToCreditInterest().compareTo(endDate) > 0) {
            try {
                ui.printMessage("Bond has reached maturity, removing bond.");
                investmentDeleteBond(targetBond.getName(), ui);
            } catch (BondException e) {
                ui.printError("Unable to delete bond after crediting interest.");
            }
        }
    }

    /**
     * Checks the investment account for bonds interest crediting and updates the bonds to the next deposit date.
     *
     * @param ui Used for printing.
     */
    @Override
    public void updateRecurringTransactions(Ui ui) {
        for (int i = 0; i < bonds.getSize(); i++) {
            Bond targetBond = bonds.get(i);
            Date endDate = targetBond.getBondEndDate();
            Calendar calendarCurrentDate = Calendar.getInstance();
            Date currentDate = calendarCurrentDate.getTime();
            Date nextDateToCreditInterest = targetBond.getNextDateToCreditInterest();
            while (currentDate.compareTo(nextDateToCreditInterest) >= 0) {
                addBondInterestDeposit(targetBond,ui);
                nextDateToCreditInterest = calculateNextInterestDate(nextDateToCreditInterest);
                targetBond.setNextDateToCreditInterest(nextDateToCreditInterest);
                removeBondIfMature(ui, targetBond, endDate);
            }
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
}
