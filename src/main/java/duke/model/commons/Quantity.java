package duke.model.commons;

import duke.logic.parser.exceptions.ParseException;

public class Quantity {
    private Integer number;
    private Double portion;

    public Quantity(Integer number) {
        this.number = number;
    }

    public Quantity(Double portion) {
        this.portion = portion;
    }


    public Quantity(Quantity toCopy) {
        this.number = toCopy.number;
        this.portion = toCopy.portion;
    }

    private boolean containsNumber() {
        if (number != null) {
            return true;
        } else {
            return false;
        }
    }

    public Integer getNumber() {
        if (containsNumber()) {
            return number;
        } else {
            throw new ParseException("quantity is not a integer here");
        }
    }

    public Double getPortion() {
        if (!containsNumber()) {
            return portion;
        } else {
            throw new ParseException("quantity is not a Double here");
        }
    }

    public String getNumberAsString() {
        return String.valueOf(number);
    }

    public String getPortionAsString() {
        return Double.toString(portion);
    }

    public Double getQuantity() {
        return portion;
    }

    public String toString() {
        return String.valueOf(portion);
    }
}
