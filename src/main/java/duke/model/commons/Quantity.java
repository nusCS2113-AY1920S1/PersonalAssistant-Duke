package duke.model.commons;

public class Quantity {
    private final Double number;

    public Quantity(double number) {
        this.number = number;
    }

    public Quantity(Quantity toCopy) {
        this.number = toCopy.number;
    }

    public Double getNumber() {
        return number;
    }

    public String getNumberAsString() {
        return String.valueOf(number);
    }

    public Double getQuantity() {
        return portion;
    }

    public String toString() {
        return String.valueOf(portion);
    }
}
