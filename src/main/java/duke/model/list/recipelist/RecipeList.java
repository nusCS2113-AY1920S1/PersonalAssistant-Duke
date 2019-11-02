package duke.model.list.recipelist;

import duke.model.task.ingredienttasks.Ingredient;
import duke.model.task.recipetasks.Recipe;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RecipeList {

    LinkedHashMap<String, Recipe> recipeLHM;

    public RecipeList() {
        this.recipeLHM = new LinkedHashMap<>();
    }

    public RecipeList(LinkedHashMap<String, Recipe> recipeListFromStorage) {
        this.recipeLHM = recipeListFromStorage;
    }

    public void addRecipe(String recipeTitle) {
        this.recipeLHM.put(recipeTitle, createNewRecipe(recipeTitle));
    }

    public Recipe deleteRecipe(String recipeTitle) {
        return this.recipeLHM.remove(recipeTitle);
    }

    public Recipe createNewRecipe(String recipeTitle) {
        return new Recipe(recipeTitle);
    }

    public LinkedHashMap<String, Recipe> getRecipeList() {
        return this.recipeLHM;
    }

    public boolean containsRecipe(String recipeTitle) {
        return this.recipeLHM.containsKey(recipeTitle);
    }

    public String containsRecipeIngredient(String recipeTitle, String recipeIngredient) {
        ArrayList<String> arrayList = new ArrayList<>(this.recipeLHM.get(recipeTitle).getRequiredIngredients().getRequiredIngredientList());
        String temp = "";

        int i = 0;
        for (String ingredient : arrayList) {
            String[] check = ingredient.split(",", 2);
            if (check[0].trim().equals(recipeIngredient)) {
                temp = ingredient + " , " + i;
            }
            i++;
        }
        if (temp.isEmpty()) {
            return "null";
        } else {
            return temp;
        }
    }

    public void editRating(String recipeTitle, String rating) {
        this.recipeLHM.get(recipeTitle).editRating(rating);
    }

    public void editFeedback(String recipeTitle, String feedback) {
        this.recipeLHM.get(recipeTitle).editFeedback(feedback);
    }

    public void editPrepTime(String recipeTitle, String prepTime) {
        this.recipeLHM.get(recipeTitle).editPrepTime(prepTime);
    }

    public void getPrepTime(String recipeTitle, String prepTime) {
        this.recipeLHM.get(recipeTitle).getPrepTime(prepTime);
    }

    public String viewRecipe(String recipeTitle) {
        return this.recipeLHM.get(recipeTitle).getViewString();
    }

    public ArrayList<Ingredient> getReqIngredients(String recipeTitle) {
        Recipe recipe = this.recipeLHM.get(recipeTitle);
        return recipe.getListOfIngredients();
    }
    public String viewReqIngredient(String recipeTitle) {
        return this.recipeLHM.get(recipeTitle).getViewReqString();
    }

    public void insertReqIngredient(String recipeTitle, String position, String ingredientName, String quantity, String unit, String additionalInfo) {
        this.recipeLHM.get(recipeTitle).getRequiredIngredients().insertIngredient(position, ingredientName, quantity, unit, additionalInfo);
    }

    public String deleteReqIngredient(String recipeTitle, String position) {
        return this.recipeLHM.get(recipeTitle).getRequiredIngredients().deleteIngredient(position);
    }

    public void appendReqIngredient(String recipeTitle, String ingredientName, String quantity, String unit, String additionalInfo) {
        this.recipeLHM.get(recipeTitle).getRequiredIngredients().appendIngredient(ingredientName, quantity, unit, additionalInfo);
    }

    public void clearReqIngredient(String recipeTitle) {
        this.recipeLHM.get(recipeTitle).getRequiredIngredients().clearIngredients();
    }

    public void removeDupReqIngredient(int index, String recipeTitle) {
        this.recipeLHM.get(recipeTitle).getRequiredIngredients().removeIngredient(index);
    }

    public void insertPrepStep(String recipeTitle, String position, String prepStep) {
        this.recipeLHM.get(recipeTitle).getPrepSteps().insertStep(position, prepStep);
    }

    public String deletePrepStep(String recipeTitle, String position) {
        return this.recipeLHM.get(recipeTitle).getPrepSteps().deleteStep(position);
    }

    public void appendPrepStep(String recipeTitle, String prepStep) {
        this.recipeLHM.get(recipeTitle).getPrepSteps().appendStep(prepStep);
    }

    public void clearPrepStep(String recipeTitle) {
        this.recipeLHM.get(recipeTitle).getPrepSteps().clearSteps();
    }

    public int getSize() {
        return this.recipeLHM.size();
    }
}