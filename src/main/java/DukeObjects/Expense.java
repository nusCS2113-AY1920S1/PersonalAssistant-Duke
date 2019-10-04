package DukeObjects;

import parser.LocalDateTimeParser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class Expense {
    protected BigDecimal amount;
    protected String description;
    protected LocalDateTime time;
    protected boolean isTentative;

    public Expense(String amount, String description){
        double amountDouble = Double.parseDouble(amount);
        time = LocalDateTime.now();
        this.amount = new BigDecimal(amountDouble).setScale(2, RoundingMode.HALF_UP);
        this.description = description;
        this.isTentative = false;
    }

    @Override
    public String toString() {
        return "$"+ amount + " " + description + " " + new LocalDateTimeParser().toString(time);
    }
}
