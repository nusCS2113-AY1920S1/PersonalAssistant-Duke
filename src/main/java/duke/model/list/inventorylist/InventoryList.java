package duke.model.list.inventorylist;

import duke.model.task.ingredienttasks.Ingredient;

import java.text.ParseException;
import java.util.HashMap;

public class InventoryList {

    private static String msg = "";
    private HashMap<String, Ingredient> inventoryHM;

    public InventoryList() {
        this.inventoryHM = new HashMap<String, Ingredient>();
    }

    public void addIngredientMass(String ingredientName, String quantity, String unit) {
        Ingredient ingred = inventoryHM.get(ingredientName);
        ingred.addMass(quantity, unit);
    }

    public void replaceAdditionalInfo(String ingredientName, String additionalInfo) {
        Ingredient ingred = inventoryHM.get(ingredientName);
        ingred.replaceInfo(additionalInfo);
    }

    public InventoryList(HashMap<String, Ingredient> inventoryListFromStorage) {
        this.inventoryHM = inventoryListFromStorage;
    }


    public boolean containsIngredient(String ingredientName) {
        if (this.inventoryHM.containsKey(ingredientName)) {
            return true;
        } else {
            return false;
        }
    }

    public void clearInventory() {
        this.inventoryHM.clear();
    }

    public void addIngredient(String ingredientName, String quantity, String unit, String additionalInfo) throws ParseException {
        inventoryHM.put(ingredientName, new Ingredient(ingredientName, quantity, unit, additionalInfo));
    }

    public Ingredient deleteIngredient(String ingredientName) {
        return this.inventoryHM.remove(ingredientName);
    }

    public int getSize() {
        return inventoryHM.size();
    }

    public HashMap<String, Ingredient> getInventoryList() {
        return inventoryHM;
    }
}