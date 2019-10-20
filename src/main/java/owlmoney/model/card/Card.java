package owlmoney.model.card;

import java.text.DecimalFormat;

import owlmoney.model.card.exception.CardException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Card class for initialisation of credit card object.
 */
public class Card {
    private String name;
    private double limit;
    private double rebate;
    private double remainingLimit;
    private TransactionList paid;
    private TransactionList unpaid;

    /**
     * Constructor that allows the child class to create an instance with credit card name.
     *
     * @param name   A name for the credit card.
     * @param limit  Credit card monthly spending limit.
     * @param rebate Credit card monthly cash back rebate.
     */
    public Card(String name, double limit, double rebate) {
        this.name = name;
        this.limit = limit;
        this.remainingLimit = limit;
        this.rebate = rebate;
        this.paid = new TransactionList();
        this.unpaid = new TransactionList();
    }

    /**
     * Gets the card name of the credit card.
     *
     * @return name of the credit card.
     */
    String getName() {
        return this.name;
    }

    /**
     * Set the card name for the credit card.
     *
     * @param name A name for the credit card.
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the card limit of the credit card.
     *
     * @return card limit of the credit card.
     */
    double getLimit() {
        return this.limit;
    }

    /**
     * Gets the remaining limit of the credit card.
     *
     * @return remaining limit of the credit card.
     */
    double getRemainingLimit() {
        return this.remainingLimit;
    }

    /**
     * Set the card limit for the credit card.
     *
     * @param limit A name for the credit card.
     */
    void setLimit(double limit) {
        this.limit = limit;
    }

    /**
     * Set the remaining limit for the credit card.
     *
     * @param remainingLimit Remaining limit for the credit card.
     */
    void setRemainingLimit(double remainingLimit) {
        this.remainingLimit = remainingLimit;
    }

    /**
     * Subtract remaining limit.
     *
     * @param amount Amount to be subtracted from remaining limit.
     */
    private void subtractRemainingLimit(double amount) {
        this.remainingLimit -= amount;
    }

    /**
     * Add remaining limit.
     *
     * @param amount Amount to be added to remaining limit.
     */
    private void addRemainingLimit(double amount) {
        this.remainingLimit += amount;
    }

    /**
     * Gets the rebate of the credit card.
     *
     * @return rebate of the credit card.
     */
    double getRebate() {
        return this.rebate;
    }

    /**
     * Set the rebate for the credit card.
     *
     * @param rebate Rebate for the credit card.
     */
    void setRebate(double rebate) {
        this.rebate = rebate;
    }

    /**
     * Gets the credit card details.
     *
     * @return String of credit card details.
     */
    public String getDetails() {
        return "Card Name: " + getName()
                + "\nMonthly Limit: $" + new DecimalFormat("0.00").format(getLimit())
                + "\nRemaining Limit: $" + new DecimalFormat("0.00").format(getRemainingLimit())
                + "\nRebate: " + new DecimalFormat("0.00").format(getRebate()) + "%";
    }

    /**
     * Checks if expenditure exceeds remaining card limit.
     *
     * @param exp Expenditure to be added.
     * @throws CardException If expenditure exceeds remaining card limit.
     */
    private void checkExpExceedRemainingLimit(Transaction exp) throws CardException {
        if (exp.getAmount() > this.getRemainingLimit()) {
            throw new CardException("Expenditure to be added cannot exceed remaining limit of $"
                    + getRemainingLimit());
        }
    }

    /**
     * Adds expenditure to the credit card.
     *
     * @param exp  Expenditure to be added.
     * @param ui   Ui of OwlMoney.
     * @param type Type of account to add expenditure into
     * @throws CardException If expenditure exceeds card limit.
     */
    void addInExpenditure(Transaction exp, Ui ui, String type) throws CardException {
        this.checkExpExceedRemainingLimit(exp);
        unpaid.addExpenditureToList(exp, ui, type);
        this.subtractRemainingLimit(exp.getAmount());
    }

    /**
     * Lists the expenditures in the current credit card.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of expenditure to list.
     * @throws TransactionException If no expenditure is found or no expenditure is in the list.
     */
    void listAllExpenditure(Ui ui, int displayNum) throws TransactionException {
        unpaid.listExpenditure(ui, displayNum);
    }

    /**
     * Deletes an expenditure in the current credit card.
     *
     * @param exId Transaction number of the transaction.
     * @param ui   Ui of OwlMoney.
     * @throws TransactionException If invalid transaction.
     */
    void deleteExpenditure(int exId, Ui ui) throws TransactionException {
        double deletedAmount = unpaid.deleteExpenditureFromList(exId, ui);
        this.addRemainingLimit(deletedAmount);
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
     * @throws CardException        If amount is negative after editing expenditure.
     */
    void editExpenditureDetails(int expNum, String desc, String amount, String date, String category, Ui ui)
            throws TransactionException, CardException {
        if (!(amount.isEmpty() || amount.isBlank()) && this.getRemainingLimit()
                + unpaid.getExpenditureAmount(expNum) < Double.parseDouble(amount)) {
            throw new CardException("New expenditure cannot exceed remaining limit of $"
                    + this.getRemainingLimit());
        }
        double oldAmount = unpaid.getExpenditureAmount(expNum);
        double newAmount = unpaid.editEx(expNum, desc, amount, date, category, ui);
        this.addRemainingLimit(oldAmount);
        this.subtractRemainingLimit(newAmount);
    }

}
