package duke.model.wallet;

import java.math.BigDecimal;
import java.time.LocalDate;

import static duke.commons.constants.DateConstants.LOCAL_DATE_FORMATTER;
public class Transaction {
    protected BigDecimal transactionAmount;
    protected String type = "";
    protected LocalDate date;

    public Transaction(String amountString, LocalDate date) {
        this.transactionAmount = new BigDecimal(amountString);
        this.date = date;
    }

    public String getType() {
        return this.type;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public BigDecimal getTransactionAmount() {
        return this.transactionAmount;
    }

    @Override
    public String toString() {
        return " SGD" + this.transactionAmount + " | " + getDate().format(LOCAL_DATE_FORMATTER);
    }
}
