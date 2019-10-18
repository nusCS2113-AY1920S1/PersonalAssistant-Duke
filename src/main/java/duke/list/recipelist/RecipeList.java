package duke.list.recipelist;

import duke.task.recipetasks.Recipe;
import duke.task.recipetasks.RecipeTitle;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;

public class RecipeList {

    LinkedHashMap<RecipeTitle, Recipe> recipeLHM;

    public RecipeList() {
        this.recipeLHM = new LinkedHashMap<>();
    }

    public RecipeList(LinkedHashMap<RecipeTitle, Recipe> recipeListFromStorage) {
        this.recipeLHM = recipeListFromStorage;
    }

    public void addRecipe(RecipeTitle recipeTitle) {
        recipeLHM.put(recipeTitle, createNewRecipe(recipeTitle));
    }

    public Recipe createNewRecipe(RecipeTitle recipeTitle) {
        return new Recipe(recipeTitle);
    }

    public LinkedHashMap<RecipeTitle, Recipe> getRecipeList() {
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
