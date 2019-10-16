package duke.model.commons;

public class Quantity {
    private Double number;

    public Quantity(double number) {
        this.number = number;
    }

    public Quantity(Quantity toCopy) {
        this.number = toCopy.number;
    }

    public Double getNumber() {
        return number;
    }

    public Double getQuantity() {
        return number;
    }

    public String toString() {
        return String.valueOf(number);
    }

    public void setQuantity(Double number) {
        this.number = number;
    }
}
