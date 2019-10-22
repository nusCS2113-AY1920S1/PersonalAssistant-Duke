package duke.model;

import java.math.BigDecimal;

public class Deposit extends Transaction{
    public Deposit(BigDecimal transactionAmount) {
        super(transactionAmount);
        super.type = "DEP";
    }

    @Override
    public String toString() {
        return "[DEP]" + super.toString();
    }
}
