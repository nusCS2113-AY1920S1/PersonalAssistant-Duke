package duke.model.commons;

import duke.logic.parser.exceptions.ParseException;

import java.text.DecimalFormat;

public class Quantity {
    private static DecimalFormat df1 = new DecimalFormat("#.#");
    private static final Double DEFAULT_NUMBER = 0.0;

    public static final String MESSAGE_LIMIT_QUANTITY = "Upper limit of quantity is 50000";

    private Double number;

    public Quantity(double number) {
        checkUpperLimit(number, MESSAGE_LIMIT_QUANTITY);
        this.number = Double.parseDouble(df1.format(number));
    }

    public Quantity(Quantity toCopy) {
        this.number = toCopy.number;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public String toString() {
        return String.valueOf(number);
    }

    public static Quantity getDefaultQuantity() {
        return new Quantity(DEFAULT_NUMBER);
    }

    private void checkUpperLimit(double number, String messageLimitQuantity) {
        if (number > 50000) {
            throw new ParseException(messageLimitQuantity);
        }
    }
}
