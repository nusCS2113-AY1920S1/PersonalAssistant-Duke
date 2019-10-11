package duke.list.recipelist;

import duke.model.recipe.Recipe;
import duke.task.recipetasks.PrepStep;
import duke.task.recipetasks.RecipeIngredient;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;

public class RecipeList {

    private static String msg = "";
    private ArrayList<Recipe> recipeList;

    private ArrayList<RecipeIngredient> recipeIngredient;
    private ArrayList<PrepStep> prepStep;
    private static String feedback;
    private static String rating;

    public RecipeList() {
        this.recipeIngredient = new ArrayList<RecipeIngredient>();
        this.prepStep = new ArrayList<PrepStep>();
        this.feedback = feedback;
        this.rating = rating;
    }

    public RecipeList(ArrayList<Recipe> recipeList) {
        this.recipeIngredient = new ArrayList<RecipeIngredient>();
        this.prepStep = new ArrayList<PrepStep>();
        this.feedback = feedback;
        this.rating = rating;
    }

    public void addRecipeIngredient(ArrayList<RecipeIngredient> recipeIngredient) throws ParseException {
        recipeList.add(new Recipe(recipeIngredient));
        int index = recipeList.size();
        if (index == 1) {
            msg = " recipe in the list.";
        } else {
            msg = MESSAGE_ITEMS2;
        }
        System.out.println(MESSAGE_ADDED + "       " + recipeList.get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + msg);
    }


    public int getSize() {
        return recipeList.size();
    }

    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }
}
