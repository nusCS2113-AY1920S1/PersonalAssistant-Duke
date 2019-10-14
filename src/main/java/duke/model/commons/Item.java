package duke.model.commons;

import duke.model.order.Quantity;
import duke.model.product.Product;
import duke.model.inventory.Ingredient;

import java.util.Objects;

public class Item<T> {
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

    public Double getTotalPrice() {
        if (item instanceof Product) {
            return ((Product) item).getRetailPrice() * (quantity.getNumber());
        }
        if (item instanceof Ingredient) {
            return ((Ingredient) item).getUnitPrice() * (quantity.getNumber());
        }
        return 0.0;
    }

}
