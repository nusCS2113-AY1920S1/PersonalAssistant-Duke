package duke.model.list.inventorylist;

import duke.model.task.ingredienttasks.Ingredient;

import java.text.ParseException;
import java.util.ArrayList;
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

    public boolean removeUsedIngredients(ArrayList<Ingredient> reqIngredients) {
        boolean isRemoved = true;
            for (Ingredient Ingredient : reqIngredients) {
                String ingredientName = Ingredient.getIngredientName();
                double ingredientMass = Ingredient.getMass();
                Ingredient inventoryIngredient = this.inventoryHM.get(ingredientName);
                double inventoryIngredientMass = inventoryIngredient.getMass();
                // if ingredient does not exist in inventory or not enough
                if (!isDeductable(ingredientMass, inventoryIngredientMass) || !isInInventory(ingredientName)) {
                    isRemoved = false;
                    break;
                } else {
                    deductMass(ingredientMass, inventoryIngredient);
                    updateQuantity(inventoryIngredient);
                }
            }
            return isRemoved;
    }

    public void deductMass(double ingredientMass, Ingredient inventoryIngredient) {
        inventoryIngredient.deductMass(ingredientMass);
    }

    public void updateQuantity(Ingredient inventoryIngredient) {
        inventoryIngredient.updateQuantity();
    }

    public boolean isDeductable(double ingredientMass, double inventoryIngredientMass) {
        if (inventoryIngredientMass - ingredientMass < 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isInInventory(String ingredientName) {
        return this.inventoryHM.containsKey(ingredientName);
    }

    public double getIngredientMass(String ingredientName) {
        Ingredient ingred = this.inventoryHM.get(ingredientName);
        return ingred.getMass();
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
