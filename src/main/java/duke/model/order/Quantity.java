package duke.model.order;

public class Quantity {
    private final Integer number;

    public Quantity(int number) {
        this.number = number;
    }

    public Quantity(Quantity toCopy) {
        this.number = toCopy.number;
    }

    public Integer getNumber() {
        return number;
    }
}
