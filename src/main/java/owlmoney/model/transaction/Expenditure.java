package owlmoney.model.transaction;

import java.time.YearMonth;
import java.util.Date;
import java.util.UUID;

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

    /**
     * Creates an instance of an overloaded new Expenditure for savings card bill expenditure.
     *
     * @param description Description of expenditure.
     * @param amount      Amount of expenditure.
     * @param date        Date of expenditure.
     * @param cardId      Id of credit card which the bill belongs to.
     * @param billDate    Credit card bill date.
     */
    public Expenditure(String description, double amount, Date date, UUID cardId, YearMonth billDate) {
        super(description, amount, date, cardId, billDate);
        this.setSpent(true);
    }
}
