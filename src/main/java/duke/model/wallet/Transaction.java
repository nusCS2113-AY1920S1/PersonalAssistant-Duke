package duke.model.wallet;

import java.math.BigDecimal;

public class Transaction {
    protected BigDecimal transactionAmount;
    protected String type = "";
    protected String date;

    public Transaction(String amountString, String dateString) {
        this.transactionAmount = new BigDecimal(amountString);
        this.date = dateString;
    }

    public String getType() {
        return this.type;
    }

    public String getDate() {
        return this.date;
    }

    public BigDecimal getTransactionAmount() {
        return this.transactionAmount;
    }

    @Override
    public String toString() {
        return " SGD" + this.transactionAmount + "|" + getDate();
    }
}
