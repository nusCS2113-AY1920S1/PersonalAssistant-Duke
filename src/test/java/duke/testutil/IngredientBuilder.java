package duke.testutil;

import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;

/**
 * A utility class to help with building Ingredient objects.
 */
public class IngredientBuilder {
    public static Item<Ingredient> buildIngredientItem(String name) {
        Item<Ingredient> ingredientItem = new Item<>(
                new Ingredient(name),
                Quantity.getDefaultQuantity());
        return ingredientItem;
    }

    public static Item<Ingredient> buildIngredientItemWithQuantity(String name, Double quantity) {
        Item<Ingredient> ingredientItem = new Item<>(
                new Ingredient(name),
                new Quantity(quantity));
        return ingredientItem;
    }
}
