package duke.testutil;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import duke.model.product.IngredientItemList;

public class IngredientItemListBuilder {
    private IngredientItemList ingredients = new IngredientItemList();

    public IngredientItemListBuilder addIngredient(Item<Ingredient> ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    public IngredientItemListBuilder addAllIngredients(Item<Ingredient> ...ingredient) {
        for(Item<Ingredient> item : ingredient) {
            ingredients.add(item);
        }
        return this;
    }

    public IngredientItemList build() {
        return ingredients;
    }
}
