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
    TransactionList paid;
    TransactionList unpaid;

    /**
     * Constructor that allows the child class to create an instance with credit card name.
     *
     * @param name A name for the credit card.
     * @param limit Credit card monthly spending limit.
     * @param rebate Credit card monthly cash back rebate.
     */
    public Card(String name, double limit, double rebate) {
        this.name = name;
        this.limit = limit;
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
     * Set the card limit for the credit card.
     *
     * @param limit A name for the credit card.
     */
    void setLimit(double limit) {
        this.limit = limit;
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
                + "\nLimit: " + new DecimalFormat("0.00").format(getLimit())
                + "\nRebate: " + new DecimalFormat("0.00").format(getRebate());
    }

    /**
     * Adds expenditure to the credit card.
     *
     */
    public void addInExpenditure(Transaction exp, Ui ui) throws CardException {
        if (exp.getAmount() > this.getLimit()) {
            throw new CardException("Expenditure to be added cannot exceed limit");
        } else {
            unpaid.addExpenditureToList(exp, ui);
        }
    }

    /**
     * Lists the expenditures in the current credit card.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of expenditure to list.
     */
    void listAllExpenditure(Ui ui, int displayNum) throws TransactionException {
        unpaid.listExpenditure(ui, displayNum);
    }

}
