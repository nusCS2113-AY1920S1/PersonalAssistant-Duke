package duke.model.commons;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
