package duke.model.wallet;

import duke.model.wallet.Transaction;

import java.math.BigDecimal;

public class Payment extends Transaction {
    public Payment(String amountString, String dateString) {
        super(amountString, dateString);
        super.type = "PAY";
    }

    @Override
    public String toString() {
        return "[PAY]" + super.toString();
    }
}
