package duke.model.wallet;

import duke.model.wallet.Transaction;

import java.math.BigDecimal;

public class Deposit extends Transaction {
    public Deposit(String amountString, String dateString) {
        super(amountString, dateString);
        super.type = "DEP";
    }

    @Override
    public String toString() {
        return "[DEP]" + super.toString();
    }
}
