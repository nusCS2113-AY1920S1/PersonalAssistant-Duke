package duke.model.wallet;

import duke.model.wallet.Transaction;

import java.math.BigDecimal;

public class Deposit extends Transaction {
    public Deposit(BigDecimal transactionAmount, String dateString) {
        super(transactionAmount, dateString);
        super.type = "DEP";
    }

    public Deposit(BigDecimal transactionAmount) {
        super(transactionAmount);
        super.type = "DEP";
    }

    @Override
    public String toString() {
        return "[DEP]" + super.toString();
    }
}
