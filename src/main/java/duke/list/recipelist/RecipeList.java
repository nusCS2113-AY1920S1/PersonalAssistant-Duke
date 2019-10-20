package duke.list.recipelist;

import duke.task.recipetasks.Recipe;

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
