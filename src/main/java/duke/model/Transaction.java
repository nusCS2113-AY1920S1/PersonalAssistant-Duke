package duke.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Transaction {
    protected BigDecimal transactionAmount;
    protected String type = "";
    protected SimpleDateFormat dateParser = new SimpleDateFormat("dd/MM/yyyy");
    protected String date = dateParser.format(Calendar.getInstance().getTime());

    public Transaction(BigDecimal transactionAmount, String dateString) {
        this.transactionAmount = transactionAmount;
        this.date = dateString;
    }

    public Transaction(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getType() {
        return this.type;
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return " SGD" + this.transactionAmount + "| " + getDate();
    }
}
