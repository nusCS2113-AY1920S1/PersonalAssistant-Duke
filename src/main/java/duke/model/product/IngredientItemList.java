package duke.model.product;

import duke.model.commons.Item;
import duke.model.inventory.Ingredient;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

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

        Collections.sort(this, Comparator.comparing(o2 -> ((Ingredient) ((Item) o2).getItem()).getName()));
        Collections.sort(list, Comparator.comparing(o2 -> ((Ingredient) ((Item) o2).getItem()).getName()));


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
