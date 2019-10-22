package owlmoney.model.transaction;

import java.util.Date;

/**
 * Contains the details of a deposit.
 */
public class Deposit extends Transaction {
    /**
     * Creates an instance of a new deposit.
     *
     * @param description Description of deposit.
     * @param amount      Amount of deposit.
     * @param date        Date of deposit.
     * @param category    Category of deposit.
     */
    public Deposit(String description, double amount, Date date, String category) {
        super(description, amount, date, category);
        this.setSpent(false);
    }
}
