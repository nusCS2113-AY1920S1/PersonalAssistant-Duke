package diyeats.model.wallet;

import java.time.LocalDate;

public class Payment extends Transaction {
    public Payment(String amountString, LocalDate date) {
        super(amountString, date);
        super.type = "PAY";
    }

    @Override
    public String toString() {
        return "[PAY]" + super.toString();
    }
}
