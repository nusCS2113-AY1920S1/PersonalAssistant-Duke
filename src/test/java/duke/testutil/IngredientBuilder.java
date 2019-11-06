package duke.testutil;

import duke.model.commons.Item;
import duke.model.commons.Quantity;
import duke.model.inventory.Ingredient;

/**
 * A utility class to help with building Ingredient objects.
 */
public class IngredientBuilder {
    public final String VALID_INGREDIENT_NAME_CREAM_CHEESE = "Cream cheese";
    public final Double VALID_INGREDIENT_QUANTITY_NUMBER_CREAM_CHEESE = 3.0;

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
