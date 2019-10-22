package duke.model;

import java.math.BigDecimal;

public class Payment extends Transaction {
    public Payment(BigDecimal transactionAmount) {
        super(transactionAmount);
        super.type = "PAY";
    }

    @Override
    public String toString() {
        return "[PAY]" + super.toString();
    }
}
