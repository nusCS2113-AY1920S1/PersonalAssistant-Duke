package duke.model.commons;

import static duke.commons.util.AppUtil.checkEmpty;
import static java.util.Objects.requireNonNull;

public class Product {
    public static final String MESSAGE_CONSTRAINTS = "Product name can take any values, "
            + "and should not be blank";

    public final String name;

    public Product(String name) {
        requireNonNull(name);
        checkEmpty(name, MESSAGE_CONSTRAINTS);

        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Product // instanceof handles nulls
                && name.equals(((Product) other).name)); // state check
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
