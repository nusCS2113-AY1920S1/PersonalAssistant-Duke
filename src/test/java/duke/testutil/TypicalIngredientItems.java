package duke.testutil;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

public class TypicalIngredientItems {
    public static final Item<Ingredient> CREAM_CHEESE_3 =
        new IngredientBuilder().buildIngredientItemWithQuantity("Cream cheese", 3.0);
    public static final Item<Ingredient> EGG_2 = new IngredientBuilder().buildIngredientItemWithQuantity(
        "Egg", 2.0);
    public static final Item<Ingredient> BUTTER_0 =
        new IngredientBuilder().buildIngredientItemWithQuantity("Butter", 0.0);
}
