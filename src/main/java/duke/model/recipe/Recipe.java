package duke.model.recipe;

import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;
import org.javatuples.Triplet;

import java.util.HashMap;
import java.util.Map;

public class Recipe {

    private String recipeTitle;
    private RecipeIngredient recipeIngredient;
//    private PrepStepList prepStep;
    private Rating rating;
    private Feedback feedback;
    private Triplet<RecipeIngredient, Rating, Feedback> recipe;

    public Recipe(Triplet<RecipeIngredient, Rating, Feedback> recipe) {
        this.recipe = recipe;
    }

    public Map<String, Recipe> toSaveString() {
        Map<String, Recipe> recipeMap = new HashMap<String, Recipe>();
        return recipeMap;
    }

    public String toString() {
        return "[Recipe Ingredient: " + recipeIngredient + "] "
                + "[Rating: " + rating + "] " + "[Feedback: " + feedback + "] ";    }
}
