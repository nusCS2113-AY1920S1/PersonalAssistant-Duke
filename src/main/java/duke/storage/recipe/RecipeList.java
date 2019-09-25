package duke.storage.recipe;

import duke.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeList extends ArrayList<Recipe> {
    private List<Recipe> recipeList = new ArrayList<Recipe>();

    public List<Recipe> getRecipeList() {
        return recipeList;
    }
}
