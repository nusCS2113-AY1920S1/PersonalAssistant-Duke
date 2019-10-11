package duke.list.recipelist;

import duke.model.recipe.Recipe;
import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.PrepStep;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;

public class RecipeList {

    private ArrayList<Recipe> recipeList;
    private static String msg = "";

    public RecipeList() {
        this.recipeList = new ArrayList<Recipe>();
    }

    public RecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public void addRecipe(String recipeTitle, RecipeIngredient recipeIngredient, Rating rating, Feedback feedback) throws ParseException {
        recipeList.add(new Recipe(recipeTitle, recipeIngredient, rating, feedback));
        int index = recipeList.size();
        if (index == 1) {
            msg = " recipe in the list.";
        } else {
            msg = " recipes in the list.";
        }
        System.out.println("New recipe added:\n" + "       " + recipeList.get(index - 1) + "\n" + "     Now you have " + index + msg);
    }

    public void deleterecipe(int i) {
        if (recipeList.size() - 1 <= 1) {
            msg = " recipe in the list.";
        } else {
            msg = " recipes in the list.";
        }
        System.out.println("     Noted. I've removed this recipe:\n" + "       " + recipeList.get(i)
                + "\n" + "     Now you have " + (recipeList.size() - 1) + msg);
        recipeList.remove(recipeList.get(i));
    }

    public ArrayList<String> viewAllRecipe() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add("     " + displayIndex + ". " + recipeList.get(i));
        }
        return arrList;
    }

    public int getSize() {
        return recipeList.size();
    }

    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }
}
