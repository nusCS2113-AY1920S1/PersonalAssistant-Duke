package duke.model.list.recipelist;

import duke.model.task.recipetasks.RecipeIngredient;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.Messages.MESSAGE_ITEMS1;

public class RecipeIngredientList {

    private static String msg = "";
    private ArrayList<RecipeIngredient> recipeIngredientList;

    public RecipeIngredientList() {
        this.recipeIngredientList = new ArrayList<RecipeIngredient>();
    }

    public RecipeIngredientList(ArrayList<RecipeIngredient> ingredientList) {
        this.recipeIngredientList = ingredientList;
    }

    public ArrayList<String> listRecipeIngredients() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayedIndex + ". " + recipeIngredientList.get(i));
        }
        return arrList;
    }

    public void addRecipeIngredient(String recipeTitle, String ingredientName, String quantity, String weight, String additonalInfo) throws ParseException {
        recipeIngredientList.add(new RecipeIngredient(recipeTitle, ingredientName, quantity, weight, additonalInfo));
        int index = recipeIngredientList.size();
        if (index == 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + recipeIngredientList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }

    // delete ingredient by index on list
    public void deleteIngredient(int i) {
        if (recipeIngredientList.size() - 1 <= 1) {
            msg = " task in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_DELETE + "       " + recipeIngredientList.get(i)
                + "\n" + MESSAGE_ITEMS1 + (recipeIngredientList.size() - 1) + msg);
        recipeIngredientList.remove(recipeIngredientList.get(i));
    }

    public int getSize() {
        return recipeIngredientList.size();
    }

    public ArrayList<RecipeIngredient> getRecipeIngredientList() {
        return recipeIngredientList;
    }

    public String toSaveString() {
        String joinedString = "";
        if (recipeIngredientList.isEmpty()) {
            joinedString = "No required ingredient.";
        }
        for (RecipeIngredient recipeIngredient : recipeIngredientList) {
            String.join(recipeIngredient.toSaveString(), joinedString);
            String.join(" | ", joinedString);
        }
        return joinedString;
    }
}
