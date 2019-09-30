package duke.entities.inventory;

import duke.entities.Ingredient;

import java.util.LinkedHashMap;

public class ShoppingList {
    private LinkedHashMap<Ingredient, Integer> ShoppingList;

    ShoppingList() {
        ShoppingList = new LinkedHashMap<>();
    }

    ShoppingList(LinkedHashMap<Ingredient, Integer> Ingredients) {
        this.ShoppingList = Ingredients;
    }

    public LinkedHashMap<Ingredient, Integer> getInventoryList() {
        return ShoppingList;
    }

    public void add(Ingredient ingredient, int quantity) {
        ShoppingList.put(ingredient, quantity);
    }

    public void remove(Ingredient ingredient) {
        ShoppingList.remove(ingredient);
    }
}
