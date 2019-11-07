package duke.model.order;

import java.util.Objects;

import static duke.commons.util.AppUtil.checkArgument;

/**
 * Represents an Orders's total price.
 * Guarantees: immutable; is valid as declared in {@link #isValidTotalPrice(double)}
 */
public class TotalPrice {
    public static final double MAX = 500000.0;
    public static final String MESSAGE_CONSTRAINTS =
        "Total price of an order should be less than " + MAX + ".";
    public final double value;

    /**
     * Creates a total price.
     *
     * @param value a valid total price.
     */
    public TotalPrice(double value) {
        checkArgument(isValidTotalPrice(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    public boolean isValidTotalPrice(double value) {
        return value <= MAX;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TotalPrice that = (TotalPrice) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
