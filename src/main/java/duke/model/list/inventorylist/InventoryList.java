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

    public InventoryList(HashMap<String, Ingredient> inventoryListFromStorage) {
        this.inventoryHM = inventoryListFromStorage;
    }

    /*
    public ArrayList<String> listOfIngredients() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayedIndex + ". " + ingredientList.get(i));
        }
        return arrList;
    }

     */

    public boolean containsIngredient(String ingredientName) {
        if (this.inventoryHM.containsKey(ingredientName)) {
            return true;
        } else {
            return false;
        }
    }


    /*
    // find ingredient to check the quantity
    public ArrayList<String> checkQuantity(String ingredientName) throws DukeException {
        for (int i = 0; i < getSize(); i++) {
            if (ingredientList.get(i).getName().contains(ingredientName)) {
                System.out.println(ingredientList.get(i).getQuantity());
            }
        }
        if (arrFind.isEmpty()) {
            throw new DukeException(ERROR_MESSAGE_NOTFOUND);
        } else {
            return arrFind;
        }
    }
    */

    public void addIngredient(String ingredientName, String quantity, String unit, String additionalInfo) throws ParseException {
        inventoryHM.put(ingredientName, new Ingredient(ingredientName, quantity, unit, additionalInfo));
    }

    public Ingredient deleteIngredient(String ingredientName) {
        return this.inventoryHM.remove(ingredientName);
    }

    /*
    // delete ingredient by index on list
    public void deleteIngredient(int i) {

        if (ingredientList.size() - 1 <= 1) {
            msg = " ingredient in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_INGREDIENT_DELETED + "       " + ingredientList.get(i)
                + "\n" + MESSAGE_ITEMS1 + (ingredientList.size() - 1) + msg);

        ingredientList.remove(ingredientList.get(i));
    }
    */

    /*
    public String get(int i) {
        return ingredientList.get(i).toString();
    }

    /*
    // delete ingredient by name
    public void deleteIngredient(String ingredientName) {
        if (ingredientList.size() - 1 <= 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
            for (int i = 0; i < getSize(); i++) {
                if (ingredientList.get(i).getName.contains(ingredientName)) {
                    ingredientList.remove(ingredientList.get(i));
                    System.out.println(MESSAGE_DELETE + "       " + ingredientList.get(i)
                            + "\n" + MESSAGE_ITEMS1 + (ingredientList.size() - 1) + msg);
                }
            }
        }
    }
    */

    public int getSize() {
        return inventoryHM.size();
    }

    public HashMap<String, Ingredient> getInventoryList() {
        return inventoryHM;
    }
}
