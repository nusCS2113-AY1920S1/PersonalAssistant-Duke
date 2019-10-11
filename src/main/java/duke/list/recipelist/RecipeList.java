package duke.list.recipelist;

import duke.model.recipe.Recipe;
import duke.task.recipetasks.PrepStep;
import duke.task.recipetasks.RecipeIngredient;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;

public class RecipeList {

    private static String msg = "";
    private Recipe recipe;

    public RecipeList(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Recipe addRecipe()
}
