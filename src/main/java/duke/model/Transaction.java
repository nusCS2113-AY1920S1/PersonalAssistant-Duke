package duke.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Transaction {
    protected BigDecimal amount;
    protected String type = "";
    protected SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy");
    protected String date = dateparser.format(Calendar.getInstance().getTime());

    public Transaction(BigDecimal amount, String type) {
        this.amount = amount;
    }

    public String getType() {
        return this.type;
    }
}
