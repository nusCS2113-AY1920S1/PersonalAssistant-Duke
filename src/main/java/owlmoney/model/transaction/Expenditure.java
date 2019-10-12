package owlmoney.model.transaction;

import java.util.Date;

/**
 * Expenditure class which represents one expenditure.
 */
public class Expenditure extends Transaction {

    /**
     * Constructor which constructs a new Expenditure.
     *
     * @param description Description of expenditure.
     * @param amount Amount of expenditure.
     * @param date Date of expenditure.
     * @param category Category of expenditure.
     */
    public Expenditure(String description, double amount, Date date, String category) {
        super(description,amount,date,category);
    }
}
