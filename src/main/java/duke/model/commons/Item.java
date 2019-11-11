package duke.model.commons;

import duke.model.inventory.Ingredient;
import duke.model.product.Product;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 * A class that contains any class that needs a quantity. For example, products and ingredients.
 */
public class Item<T> {
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private final T item;
    private final Quantity quantity;

    public Item(T item, Quantity quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public T getItem() {
        return item;
    }

    public Quantity getQuantity() {
        return new Quantity(quantity);
    }

    public void setQuantity(Double number) {
        quantity.setNumber(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item<?> item1 = (Item<?>) o;
        return Objects.equals(item, item1.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(item);
    }

    /**
     * Calculates the total price of the item for products and ingredients.
     * If not product or ingredient, return 0.0
     *
     * @return the total price calculated
     */
    public Double getTotalPrice() {
        if (item instanceof Product) {
            Double totalPrice = ((Product) item).getRetailPrice() * (quantity.getNumber());
            return Double.parseDouble(df2.format(totalPrice));
        }
        if (item instanceof Ingredient) {
            Double totalPrice = ((Ingredient) item).getUnitPrice() * (quantity.getNumber());
            return Double.parseDouble(df2.format(totalPrice));
        }
        return 0.0;
    }

}
