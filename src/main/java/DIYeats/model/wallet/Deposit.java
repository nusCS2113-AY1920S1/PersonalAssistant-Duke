package DIYeats.model.wallet;

import java.time.LocalDate;

public class Deposit extends Transaction {
    public Deposit(String amountString, LocalDate date) {
        super(amountString, date);
        super.type = "DEP";
    }

    @Override
    public String toString() {
        return "[DEP]" + super.toString();
    }
}
