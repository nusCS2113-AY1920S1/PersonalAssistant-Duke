package duke.model.order;

public class Quantity {
    private final String defaultUnit = "piece";

    private final Integer number;
    private final String unit;

    public Quantity(int number, String unit) {
        this.number = number;
        this.unit = unit;
    }

    public Quantity(Integer number) {
        this(number, "piece");
    }

    public Integer getNumber() {
        return number;
    }

    public String getUnit() {
        return unit;
    }
}
