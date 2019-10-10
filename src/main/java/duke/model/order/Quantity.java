package duke.model.order;

public class Quantity {
    private static final String defaultUnit = "piece";

    private final Integer number;
    private final String unit;

    public Quantity(int number, String unit) {
        this.number = number;
        this.unit = unit;
    }

    public Quantity(Integer number) {
        this(number, defaultUnit);
    }

    public Integer getNumber() {
        return number;
    }

    public String getUnit() {
        return unit;
    }
}
