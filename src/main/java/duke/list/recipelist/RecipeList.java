package duke.list.recipelist;

import duke.task.recipetasks.Recipe;
import duke.task.recipetasks.RecipeTitle;
import duke.task.recipetasks.RequiredIngredients;
import javafx.scene.effect.SepiaTone;

import java.util.*;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;

public class RecipeList {

    HashMap<Integer, Recipe> recipeLHM;

    public RecipeList() {
        this.recipeLHM = new HashMap<>();
    }

    public RecipeList(HashMap<Integer, Recipe> recipeListFromStorage) {
        this.recipeLHM = recipeListFromStorage;
    }

    public String getRecipeIndex() {
        Set entry = this.recipeLHM.keySet();
        return entry.toString();
    }

    public void addRecipe(int index, String recipeTitle) {
        this.recipeLHM.put(index, createNewRecipe(recipeTitle));
    }

    public void addRecipeIngredient(String recipeIndex, String recipeIngredientName, String quantity, String unit, String additionalInfo) {
        Recipe value = this.recipeLHM.get(Integer.parseInt(recipeIndex));
        System.out.println("this is the value recipelist: " + value);
        System.out.println("this is the value recipe title recipelist: " + value.getRecipeTitle());
        System.out.println("this is the value required ingredients recipelist: " + value.getRequiredIngredients().toSaveString());
        this.recipeLHM.put(Integer.parseInt(recipeIndex), new Recipe(value.getRecipeTitle(), new RequiredIngredients(recipeIngredientName, quantity, unit, additionalInfo)));
    }

    public Recipe deleteRecipe(String recipeTitle) {
        Recipe value;
        return value = this.recipeLHM.remove(recipeTitle);
    }

    public Recipe createNewRecipe(String recipeTitle) {
        return new Recipe(recipeTitle);
    }

    public HashMap<Integer, Recipe> getRecipeList() {
        return this.recipeLHM;
    }

    public boolean containsRecipe(String recipeTitle) {
        Map.Entry entry = (Map.Entry) this.recipeLHM.entrySet();
        Recipe recipe = (Recipe) entry.getValue();
        if (recipeTitle.equals(recipe.getRecipeTitle())) {
            return true;
        } else {
            return false;
        }
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