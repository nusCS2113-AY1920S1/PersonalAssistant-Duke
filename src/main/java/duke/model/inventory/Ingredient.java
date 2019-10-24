package duke.model.inventory;

import java.text.DecimalFormat;
import java.util.Objects;

import static duke.commons.util.AppUtil.checkArgument;
import static duke.commons.util.AppUtil.checkEmpty;
import static duke.commons.util.AppUtil.checkNegativeDouble;
import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class Ingredient {
    private static final String VALIDATION_FLOAT_NUMBER_REGEX = "^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$";
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private static final String MESSAGE_CONSTRAINTS_NAME = "Ingredient name can take any values, "
            + "and should not be blank";
    private static final String MESSAGE_CONSTRAINTS_PRICE = "Price must be a valid non-negative number";

    private static final Double DEFAULT_PRICE = 0.00;
    private static final String DEFAULT_REMARKS = "-";

    public final String name;
    public final Double unitPrice;
    public final String remarks;

    /**
     * Creates an ingredient.
     *
     * @param name      of the ingredient.
     * @param unitPrice the price of the ingredient per unit.
     * @param remarks      of the ingredient. For example, "kg", "liter", and additional info.
     */
    public Ingredient(String name, Double unitPrice, String remarks) {
        requireAllNonNull(name, unitPrice, remarks);
        checkEmpty(name, MESSAGE_CONSTRAINTS_NAME);
        checkNegativeDouble(unitPrice, MESSAGE_CONSTRAINTS_PRICE);
        checkArgument(String.valueOf(unitPrice).matches(VALIDATION_FLOAT_NUMBER_REGEX), MESSAGE_CONSTRAINTS_PRICE);

        this.name = name;
        this.unitPrice = Double.parseDouble(df2.format(unitPrice));
        this.remarks = remarks;
    }

    public Ingredient(String name) {
        this(name, DEFAULT_PRICE, DEFAULT_REMARKS);
    }

    public Ingredient(String name, String remarks) {
        this(name, DEFAULT_PRICE, remarks);
    }

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
                + ", remarks='" + remarks + '\''
                + '}';
    }

    public String getName() {
        return name;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public String getRemarks() {
        return remarks;
    }
}
