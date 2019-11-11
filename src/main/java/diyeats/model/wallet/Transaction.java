package diyeats.model.wallet;

import java.math.BigDecimal;
import java.time.LocalDate;

import static diyeats.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;

/**
 * Transaction is a public class that representing a transaction by the user.
 */
public class Transaction {
    protected BigDecimal transactionAmount;
    protected String type = "";
    protected LocalDate date;

    /**
     * This is the constructor of transaction object.
     * @param amountString the amount of money in the transaction in SGD.
     * @param date the date the transaction occur.
     */
    public Transaction(String amountString, LocalDate date) {
        this.transactionAmount = new BigDecimal(amountString);
        this.date = date;
    }

    /**
     * the getter for transaction type.
     * @return transaction type.
     */
    public String getType() {
        return this.type;
    }

    /**
     * the getter for the transaction date.
     * @return transaction date.
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * the getter for transaction amount.
     * @return transaction amount.
     */
    public BigDecimal getTransactionAmount() {
        return this.transactionAmount;
    }

    //to facilitate undo
    public void changeType() {
        if (this.type.equals("PAY")) {
            this.type = "DEP";
        } else {
            this.type = "PAY";
        }
    }

    /**
     * This function overrides the toString() function in object.
     * @return the String representation of a Transaction object.
     */
    @Override
    public String toString() {
        return "[" + getType() + "]" + " SGD" + this.transactionAmount + " | " + getDate().format(LOCAL_DATE_FORMATTER);
    }
}
