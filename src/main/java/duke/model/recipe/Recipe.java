package duke.model.recipe;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String recipeTitle;
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
}
