package duke.list.recipelist;

import duke.model.recipe.Recipe;
import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.PrepStep;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Triplet;

import java.text.ParseException;
import java.util.*;

import static duke.common.Messages.*;

public class RecipeList {

    private Map<String, Recipe> recipeList;
    private static String msg = "";

    public RecipeList() {
        this.recipeList = new HashMap<>();
    }

    public RecipeList(Map<String, Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void addRecipe(String recipeTitle, Triplet<RecipeIngredient, Rating, Feedback> recipe) throws ParseException {
        recipeList.put(recipeTitle, new Recipe(recipe));
        int index = recipeList.size();
        if (index == 1) {
            msg = " recipe in the list.";
        } else {
            msg = " recipes in the list.";
        }
        System.out.println("New recipe added:\n" + "       " + recipeList.get(index - 1) + "\n" + "     Now you have " + index + msg);
    }

//    public void deleterecipe(int i) {
//        if (recipeList.size() - 1 <= 1) {
//            msg = " recipe in the list.";
//        } else {
//            msg = " recipes in the list.";
//        }
//        System.out.println("     Noted. I've removed this recipe:\n" + "       " + recipeList.get(i)
//                + "\n" + "     Now you have " + (recipeList.size() - 1) + msg);
//        recipeList.remove(recipeList.get(i));
//    }
//
//    public ArrayList<String> viewAllRecipe() {
//        ArrayList<String> arrList = new ArrayList<>();
//        for (int i = 0; i < getSize(); i++) {
//            final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
//            arrList.add("     " + displayIndex + ". " + recipeList.get(i));
//        }
//        return arrList;
//    }

    public int getSize() {
        return recipeList.size();
    }

    public Map<String, Recipe> getRecipeList() {
        return recipeList;
    }

    public Collection getValue() {
        return recipeList.values();
    }
}
