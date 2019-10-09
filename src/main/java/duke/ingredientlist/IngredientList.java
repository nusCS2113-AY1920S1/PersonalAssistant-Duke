package duke.ingredientlist;

import duke.task.ingredienttasks.Ingredient;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.Messages.MESSAGE_ITEMS1;

public class IngredientList {

    private static String msg = "";
    private ArrayList<Ingredient> ingredientList;

    public IngredientList() {
        this.ingredientList = new ArrayList<Ingredient>();
    }

    public IngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public ArrayList<String> listIngredients() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayedIndex + ". " + ingredientList.get(i));
        }
        return arrList;
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

    public void addIngredient(String ingredientName, int Quantity) throws ParseException {
        ingredientList.add(new Ingredient(ingredientName, Quantity));
        int index = ingredientList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + ingredientList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    // delete ingredient by index on list
    public void deleteIngredient(int i) {
        if (ingredientList.size() - 1 <= 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_DELETE + "       " + ingredientList.get(i)
                + "\n" + MESSAGE_ITEMS1 + (ingredientList.size() - 1) + msg);
        ingredientList.remove(ingredientList.get(i));
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
        return ingredientList.size();
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }
}
