package diyeats.model.wallet;

import java.time.LocalDate;

//@@author GaryStu
/**
 * Payment is a public class that inherits from the transaction class.
 */
public class Payment extends Transaction {
    /**
     * Constructor for payment class.
     * @param amountString the amount of money to be paid.
     * @param date the date when the payment occurs.
     */
    public Payment(String amountString, LocalDate date) {
        super(amountString, date);
        super.type = "PAY";
    }
}
