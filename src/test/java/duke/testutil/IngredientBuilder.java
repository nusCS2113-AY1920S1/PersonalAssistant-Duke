package duke.testutil;

import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;

/**
 * A utility class to help with building Ingredient objects.
 */
public class IngredientBuilder {
    private static final Double DEFAULT_PRICE = 0.00;
    private static final Double DEFAULT_QUANTITY = 0.0;
    private static final String DEFAULT_REMARKS = "";

    private static Item<Ingredient> ingredientItem;

    public IngredientBuilder(String name) {
        ingredientItem = new Item<>(new Ingredient(name), Quantity.getDefaultQuantity());
    }

    public IngredientBuilder withCost(Double cost) {
        ingredientItem.getItem().setUnitPrice(cost);
        return this;
    }

    public IngredientBuilder withQuantity(Double quantity) {
        ingredientItem.setQuantity(quantity);
        return this;
    }

    public IngredientBuilder withRemarks(String remarks) {
        ingredientItem.getItem().setRemarks(remarks);
        return this;
    }

    public static Item<Ingredient> buildIngredientItem(String name) {
        ingredientItem = new Item<>(
                new Ingredient(name),
                Quantity.getDefaultQuantity());
        return ingredientItem;
    }

    public static Item<Ingredient> buildIngredientItemWithQuantity(String name, Double quantity) {
        ingredientItem = new Item<>(
                new Ingredient(name),
                new Quantity(quantity));
        return ingredientItem;
    }

    public Item<Ingredient> build() {
        return ingredientItem;
    }
}
