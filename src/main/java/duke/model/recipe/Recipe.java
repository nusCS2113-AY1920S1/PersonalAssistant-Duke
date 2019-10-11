package duke.model.recipe;

import duke.list.recipelist.RecipeIngredientList;
import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.PrepStep;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;
import org.javatuples.Triplet;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Recipe> recipeMap = new Map<String, Recipe>(recipeTitle, recipe);
        return recipeMap;
    }

    public String toString() {
        return "[Recipe Ingredient: " + recipeIngredient + "] "
                + "[Rating: " + rating + "] " + "[Feedback: " + feedback + "] ";    }

//    public String getRecipeTitle() {
//        return recipeTitle;
//    }
//
//    public RecipeIngredientList getRecipeTngredientList() {
//        return recipeIngredientList;
//    }
//
//    public Rating getRating() {
//        return rating;
//    }
//
//    public Feedback getFeedback() {
//        return feedback;
//    }

//    public void addIngredient(RecipeIngredient recipeIngredient) {
//        recipeIngredient.add(recipeIngredient);
//    }

//    public void addPrepStep(PrepStep prepstep) { prepStep.add(prepstep); }

//    public void addRating(Rating rating) { this.rating = rating; }
//
//    public void addFeedback(Feedback feedback) { this.feedback = feedback; }
}
