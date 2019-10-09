package duke.model.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static duke.commons.util.AppUtil.checkEmpty;
import static duke.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a product in product list.
 */
public class Product {
    public static final String MESSAGE_CONSTRAINTS = "Product name can take any values, "
            + "and should not be blank";

    //Identity field
    public final String name;

    public final Map<Ingredient, Double> ingredients; //TODO: maybe add a seperate class

    /**
     * All fields should not be null.
     */
    public Product(String name, Map<Ingredient, Double> ingredients) {
        requireAllNonNull(name, ingredients);
        checkEmpty(name, MESSAGE_CONSTRAINTS);

        this.name = name;
        this.ingredients = ingredients;
    }

    public Product(String name) {
        this(name, new HashMap<>());
    }


    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
