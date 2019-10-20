package duke.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;
import duke.model.product.Product;

public class JsonAdaptedProductItem {
    private final JsonAdaptedProduct product;
    private final double amount;

    /**
     * Constructs a {@code JsonAdaptedProductItem} with the given details.
     */
    @JsonCreator
    public JsonAdaptedProductItem(
            @JsonProperty("product") JsonAdaptedProduct product,
            @JsonProperty("amount") double amount) {
        this.product = product;
        this.amount = amount;
    }

    /**
     * Converts a given {@code Item<Product>} into this class for Jackson use.
     */
    public JsonAdaptedProductItem(Item<Product> source) {
        this.product = new JsonAdaptedProduct(source.getItem());
        this.amount = source.getQuantity().getNumber();
    }

    /**
     * Converts this Jackson-friendly adapted object into the model's {@code Item<Product>} object.
     */
    public Item<Product> toModelType() {
        return new Item<Product>(
                product.toModelType(),
                new Quantity(amount)
        );
    }
}
