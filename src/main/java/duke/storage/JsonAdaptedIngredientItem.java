package duke.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;

public class JsonAdaptedIngredientItem {
    private final JsonAdaptedIngredient ingredient;
    private final double amount;

    /**
     * Constructs a {@code JsonAdaptedIngredientItem} with the given details.
     */
    @JsonCreator
    public JsonAdaptedIngredientItem(
            @JsonProperty("ingredient") JsonAdaptedIngredient ingredient,
            @JsonProperty("amount") double amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    /**
     * Converts a given {@code Item<Ingredient>} into this class for Jackson use.
     */
    public JsonAdaptedIngredientItem(Item<Ingredient> source) {
        this.ingredient = new JsonAdaptedIngredient(source.getItem());
        this.amount = source.getQuantity().getNumber();
    }

    /**
     * Converts this Jackson-friendly adapted object into the model's {@code Item<Ingredient>} object.
     */
    public Item<Ingredient> toModelType() {
        return new Item<Ingredient>(
                ingredient.toModelType(),
                new Quantity(amount)
        );
    }
}
