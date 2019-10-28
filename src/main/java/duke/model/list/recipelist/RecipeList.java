package duke.model.list.recipelist;

import duke.model.task.recipetasks.Recipe;
import duke.model.task.recipetasks.RequiredIngredients;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;

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

//    public void addRecipeIngredient(String recipeTitle, String recipeIngredientName, String quantity, String unit, String additionalInfo) {
//        Recipe value = this.recipeLHM.get(recipeTitle);
//        System.out.println("this is the value recipelist: " + value);
//        System.out.println("this is the value recipe title recipelist: " + value.getRecipeTitle());
//        System.out.println("this is the value required ingredients recipelist: " + value.getRequiredIngredients().toSaveString());
//        this.this.recipeLHM.put(recipeTitle, new Recipe(recipeTitle, new RequiredIngredients(recipeIngredientName, quantity, unit, additionalInfo)));
//    }

    public Recipe deleteRecipe(String recipeTitle) {
        Recipe value;
        return value = this.recipeLHM.remove(recipeTitle);
    }

    public Recipe createNewRecipe(String recipeTitle) {
        return new Recipe(recipeTitle);
    }

    public LinkedHashMap<String, Recipe> getRecipeList() {
        return this.recipeLHM;
    }

    public boolean containsRecipe(String recipeTitle) {
        if (this.recipeLHM.containsKey(recipeTitle)) {
            System.out.println("true fefesges");
            return true;
        } else {
            System.out.println("false fefesges");
            return false;
        }
    }

    public int containsRecipeIngredient(String recipeTitle, String recipeIngredient) {
        ArrayList<String> arrayList = new ArrayList<>(this.recipeLHM.get(recipeTitle).getRequiredIngredients().getRequiredIngredientList());
        if (arrayList.contains(recipeIngredient)) {
            System.out.println("this is the position of the duplicate ingredient " + arrayList.indexOf(recipeIngredient));
            return arrayList.indexOf(recipeIngredient);
        } else {
            return -1;
        }
    }

    public void editRating(String recipeTitle, String rating) {
        this.recipeLHM.get(recipeTitle).editRating(rating);
    }

    public void editFeedback(String recipeTitle, String feedback) {
        this.recipeLHM.get(recipeTitle).editFeedback(feedback);
    }
    public String viewRecipe(String recipeTitle) {
        return this.recipeLHM.get(recipeTitle).getViewString();
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



    public ArrayList<String> listRecipeTitle() {
        ArrayList<String> arrList = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrList.add(this.recipeLHM.get(i).getRecipeTitle().toString());
        }
        return arrList;
    }

    public int getSize() {
        return this.recipeLHM.size();
    }
}