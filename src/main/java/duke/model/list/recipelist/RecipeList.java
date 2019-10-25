package duke.model.list.recipelist;

import duke.model.task.recipetasks.Recipe;
import duke.model.task.recipetasks.RequiredIngredients;

import java.util.*;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;

public class RecipeList {

    HashMap<String, Recipe> recipeLHM;

    public RecipeList() {
        this.recipeLHM = new HashMap<>();
    }

    public RecipeList(HashMap<String, Recipe> recipeListFromStorage) {
        this.recipeLHM = recipeListFromStorage;
    }

    public String getRecipeIndex() {
        Set entry = this.recipeLHM.keySet();
        return entry.toString();
    }

    public void addRecipe(String recipeTitle) {
        this.recipeLHM.put(recipeTitle, createNewRecipe(recipeTitle));
    }

    public void addRecipeIngredient(String recipeTitle, String recipeIngredientName, String quantity, String unit, String additionalInfo) {
        Recipe value = this.recipeLHM.get(recipeTitle);
        System.out.println("this is the value recipelist: " + value);
        System.out.println("this is the value recipe title recipelist: " + value.getRecipeTitle());
        System.out.println("this is the value required ingredients recipelist: " + value.getRequiredIngredients().toSaveString());
        this.recipeLHM.put(recipeTitle, new Recipe(recipeTitle, new RequiredIngredients(recipeIngredientName, quantity, unit, additionalInfo)));
    }

    public Recipe deleteRecipe(String recipeTitle) {
        Recipe value;
        return value = this.recipeLHM.remove(recipeTitle);
    }

    public Recipe createNewRecipe(String recipeTitle) {
        return new Recipe(recipeTitle);
    }

    public HashMap<String, Recipe> getRecipeList() {
        return this.recipeLHM;
    }

    public boolean containsRecipe(String recipeTitle) {
        return this.recipeLHM.containsKey(recipeTitle);
    }

    public ArrayList<String> listRecipeTitle() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add(recipeLHM.get(i).getRecipeTitle().toString());
        }
        return arrList;
    }

    public int getSize() {
        return this.recipeLHM.size();
    }
}