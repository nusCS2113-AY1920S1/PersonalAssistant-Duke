package duke.testutil;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

public class TypicalIngredientItems {
    public static final Item<Ingredient> CREAM_CHEESE_3 =
        new IngredientBuilder("Cream cheese").withQuantity(3.0).build();
    public static final Item<Ingredient> EGG_2 =
            new IngredientBuilder("Egg").withQuantity(2.0).build();
    public static final Item<Ingredient> BUTTER_0 =
        new IngredientBuilder("Butter").withQuantity(0.0).build();
}
