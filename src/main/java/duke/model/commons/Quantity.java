package duke.model.commons;

import static duke.commons.util.AppUtil.checkNegativeDouble;

public class Quantity {
    private static final Double DEFAULT_NUMBER = 0.0;
    private Double number;

    private static final String MESSAGE_CONSTRAINTS_QUANTITY = "Quantity must be a valid non-negative number";

    public Quantity(double number) {
        checkNegativeDouble(number, MESSAGE_CONSTRAINTS_QUANTITY);
        this.number = number;
    }

    public Quantity(Quantity toCopy) {
        this.number = toCopy.number;
    }

    public Double getNumber() {
        return number;
    }

    public String toString() {
        return String.valueOf(number);
    }

    public static Quantity getDefaultQuantity() {
        return new Quantity(DEFAULT_NUMBER);
    }
}
