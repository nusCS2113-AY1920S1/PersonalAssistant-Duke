package duke.model.wallet;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static duke.commons.constants.DateConstants.DATE_FORMAT;

public class Transaction {
    protected SimpleDateFormat dateParser = DATE_FORMAT;
    protected BigDecimal transactionAmount;
    protected String type = "";
    protected String date = dateParser.format(Calendar.getInstance().getTime());

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
