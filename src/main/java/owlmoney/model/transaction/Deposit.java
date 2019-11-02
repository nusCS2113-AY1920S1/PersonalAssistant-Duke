package owlmoney.model.transaction;

import java.time.YearMonth;
import java.util.Date;
import java.util.UUID;

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

    /**
     * Creates an instance of an overloaded new Deposit for savings card bill deposit.
     *
     * @param description Description of deposit.
     * @param amount      Amount of deposit.
     * @param date        Date of deposit.
     * @param cardId      Id of credit card which the bill belongs to.
     * @param billDate    Credit card bill date.
     */
    public Deposit(String description, double amount, Date date, UUID cardId, YearMonth billDate) {
        super(description, amount, date, cardId, billDate);
        this.setSpent(false);
    }
}
