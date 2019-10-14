package duke.model.commons;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

import java.util.Objects;

import static duke.commons.util.AppUtil.checkArgument;
import static duke.commons.util.AppUtil.checkEmpty;
import static duke.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents an ingredient of a product.
 */
public class Ingredient {
    private static final String VALIDATION_FLOAT_NUMBER_REGEX = "^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$";

    private static final String MESSAGE_CONSTRAINTS_NAME = "Ingredient name can take any values, "
            + "and should not be blank";
    private static final String MESSAGE_CONSTRAINTS_UNIT = "Unit can take any values, "
            + "and should not be blank";
    private static final String MESSAGE_CONSTRAINTS_PRICE = "Price is a float number";

    private static final String DEFAULT_PRICE = "5.0";
    private static final String DEFAULT_UNIT = "unit";

    public final String name;
    public final double unitPrice;
    public final String unit;

    /**
     * Creates an ingredient.
     *
     * @param name      of the ingredient.
     * @param unitPrice the price of the ingredient per unit.
     * @param unit      of the ingredient. For example, "kg", "liter".
     */
    public Ingredient(String name, String unitPrice, String unit) {
        requireAllNonNull(name, unitPrice, unit);
        checkEmpty(name, MESSAGE_CONSTRAINTS_NAME);
        checkEmpty(unit, MESSAGE_CONSTRAINTS_UNIT);
        checkArgument(unitPrice.matches(VALIDATION_FLOAT_NUMBER_REGEX), MESSAGE_CONSTRAINTS_PRICE);

        this.name = name;
        this.unitPrice = Double.parseDouble(unitPrice);
        this.unit = unit;
    }

    public Ingredient(String name) {
        this(name, DEFAULT_PRICE, DEFAULT_UNIT);
    }

    public Ingredient(String name, String unit) { this(name, DEFAULT_PRICE, unit); }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Ingredient{"
                + "name='" + name + '\''
                + ", unitPrice=" + unitPrice
                + ", unit='" + unit + '\''
                + '}';
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}
