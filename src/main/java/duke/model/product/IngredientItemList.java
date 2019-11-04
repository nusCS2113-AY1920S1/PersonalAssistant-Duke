package duke.model.product;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;

import java.util.ArrayList;

public class IngredientItemList extends ArrayList<Item<Ingredient>> {


    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.size(); i++) {
            Item<Ingredient> ingredientItem = this.get(i);
            s += "[" + ingredientItem.getItem().getName() + "," + ingredientItem.getQuantity() + "]";
        }
        return s;
    }

    /**
     * Checks if two ingredientItemLists are equal
     */
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
        String content = "|";
        boolean result = true;
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).getItem().getName().equals(list.get(i).getItem().getName())
                || !this.get(i).getQuantity().getNumber().equals(list.get(i).getQuantity().getNumber())) {
                result = false;
                break;
            }
        }
        return result;
    }
}
