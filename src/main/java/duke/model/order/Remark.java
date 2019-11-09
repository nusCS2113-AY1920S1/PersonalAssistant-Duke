package duke.model.order;

import java.util.Objects;

import static duke.commons.util.AppUtil.checkArgument;

/**
 * Represents an Orders's remark.
 * Guarantees: immutable; is valid as declared in {@link #isValidRemark(String)}
 */
public class Remark {
    public static final String MESSAGE_CONSTRAINTS =
        "Remarks should be no more than 50 characters";
    public final String value;

    /**
     * Creates a {@code Remark}.
     *
     * @param value a valid remark.
     */
    public Remark(String value) {
        assert (value != null);
        checkArgument(isValidRemark(value), MESSAGE_CONSTRAINTS);
        this.value = value;
    }

    public static boolean isValidRemark(String value) {
        return value.length() <= 50;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Remark remark = (Remark) o;
        return Objects.equals(value, remark.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

