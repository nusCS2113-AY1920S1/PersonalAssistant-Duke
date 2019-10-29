package duke.model.product;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.ArrayList;

public class IngredientItemList extends ArrayList<Item<Ingredient>> {

    public Double getIngredientCost() {
        Double ingredientCost = 0.0;
        for(int i = 0; i < this.size(); i++) {
            ingredientCost += this.get(i).getTotalPrice();
        }
        return ingredientCost;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.size(); i++) {
            Item<Ingredient> ingredientItem = this.get(i);
            s += "[" + ingredientItem.getItem().getName() + "," + ingredientItem.getQuantity() + "]" + "\n";
        }
        return s;
    }

    /** Checks if two ingredientItemLists are equal */
    public boolean listEquals(Object o) {
        IngredientItemList list = (IngredientItemList) o;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (list.size() != this.size()) {
            return false;
        }
        boolean result = true;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getItem().getName() != list.get(i).getItem().getName()
                    || this.get(i).getQuantity() != list.get(i).getQuantity()) {
                result = false;
                break;
            };
        }
        return result;
    }
}
