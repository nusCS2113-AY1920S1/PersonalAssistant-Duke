package duke.model.recipe;

import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.PrepStep;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String recipeTitle;
    private List<RecipeIngredient> recipeIngredient = new ArrayList<>();
    private List<PrepStep> prepStep = new ArrayList<>();
    private Rating rating;
    private Feedback feedback;

    public Recipe(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public Recipe(ArrayList<RecipeIngredient> ingredient) {
    }

    public void addIngredient(RecipeIngredient ingredient) {
        recipeIngredient.add(ingredient);
    }

    public void addPrepStep(PrepStep prepstep) { prepStep.add(prepstep); }

    public void addRating(Rating rating) { this.rating = rating; }

    public void addFeedback(Feedback feedback) { this.feedback = feedback; }
}
