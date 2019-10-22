package owlmoney.model.transaction;

import java.util.Date;

/**
 * Contains the details of an expenditure.
 */
public class Expenditure extends Transaction {
    /**
     * Creates an instance of a new Expenditure.
     *
     * @param description Description of expenditure.
     * @param amount      Amount of expenditure.
     * @param date        Date of expenditure.
     * @param category    Category of expenditure.
     */
    public Expenditure(String description, double amount, Date date, String category) {
        super(description, amount, date, category);
        this.setSpent(true);
    }
}
