package duke.list.recipelist;

import duke.task.recipetasks.Recipe;
import duke.task.recipetasks.RecipeTitle;
import javafx.scene.effect.SepiaTone;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;

public class RecipeList {

    LinkedHashMap<Integer, Recipe> recipeLHM;

    public RecipeList() {
        this.recipeLHM = new LinkedHashMap<>();
    }

    public RecipeList(LinkedHashMap<Integer, Recipe> recipeListFromStorage) {
        this.recipeLHM = recipeListFromStorage;
    }

    public String getRecipeIndex() {
        Map.Entry entry = (Map.Entry) this.recipeLHM.entrySet();
        return entry.getKey().toString();
    }

    public void addRecipe(int index, String recipeTitle) {
        this.recipeLHM.put(index, createNewRecipe(recipeTitle));
    }

    public Recipe deleteRecipe(String recipeTitle) {
        Recipe value;
        return value = this.recipeLHM.remove(recipeTitle);
    }

    public Recipe createNewRecipe(String recipeTitle) {
        return new Recipe(recipeTitle);
    }

    public LinkedHashMap<Integer, Recipe> getRecipeList() {
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