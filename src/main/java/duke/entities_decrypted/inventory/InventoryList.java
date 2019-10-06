package duke.entities_decrypted.inventory;

import duke.entities_decrypted.Ingredient;

import java.util.LinkedHashMap;

public class InventoryList {
    private LinkedHashMap<Ingredient, Integer> InventoryList;

    InventoryList() {
        InventoryList = new LinkedHashMap<>();
    }

    InventoryList(LinkedHashMap<Ingredient, Integer> Ingredients) {
        this.InventoryList = Ingredients;
    }

    public LinkedHashMap<Ingredient, Integer> getInventoryList() {
        return InventoryList;
    }

    public void add(Ingredient ingredient, int quantity) {
        InventoryList.put(ingredient, quantity);
    }

    public void remove(Ingredient ingredient) {
        InventoryList.remove(ingredient);
    }
}
