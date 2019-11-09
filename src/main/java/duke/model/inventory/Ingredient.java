package duke.model.inventory;

import java.text.DecimalFormat;
import java.util.Objects;

import static duke.commons.util.AppUtil.checkArgument;
import static duke.commons.util.CollectionUtil.requireAllNonNull;

public class Ingredient {
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private static final String MESSAGE_CONSTRAINTS_NAME = "Ingredient's name cannot be blank and must be "
            + "20 characters or less";
    public static final String MESSAGE_CONSTRAINTS_REMARKS = "Remarks should be no more than 50 characters";

    private static final Double DEFAULT_PRICE = 0.00;
    private static final String DEFAULT_REMARKS = "";

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
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS_NAME);
        checkArgument(isValidRemark(remarks), MESSAGE_CONSTRAINTS_REMARKS);

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

    public static boolean isValidRemark(String remark) {
        return remark.length() <= 50;
    }

    public static boolean isValidName(String name) {
        return !name.isBlank() && name.length() <= 20;
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
