package duke.model.product;

import duke.model.inventory.Ingredient;
import duke.model.commons.Item;

import java.util.ArrayList;
import java.util.List;

public class IngredientItemList {
    private List<Item<Ingredient>> ingredientItemList = new ArrayList<Item<Ingredient>>();

    public Double getIngredientCost() {
        Double ingredientCost = 0.0;
        for(int i = 0; i < ingredientItemList.size(); i++) {
            ingredientCost += ingredientItemList.get(i).getTotalPrice();
        }
        return ingredientCost;
    }
}
