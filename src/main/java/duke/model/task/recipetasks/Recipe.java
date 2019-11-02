package duke.model.task.recipetasks;

import duke.model.task.ingredienttasks.Ingredient;

import java.util.ArrayList;

import static duke.common.RecipeMessages.*;

public class Recipe {

    RecipeTitle recipeTitle;
    PrepTime prepTime;
    Rating rating;
    PrepSteps prepSteps;
    RequiredIngredients requiredIngredients; // requiredIngredients is a list of recipeIngredient objects.
    Feedback feedback;

    public Recipe(String recipeTitle) {
        this.recipeTitle = new RecipeTitle(recipeTitle);
        this.prepTime = new PrepTime();
        this.rating = Rating.UNRATED;
        this.prepSteps = new PrepSteps();
        this.requiredIngredients = new RequiredIngredients();
        this.feedback = new Feedback();
    }

    public Recipe(String recipeTitle, String prepTime, String rating, String prepSteps, String requiredIngredients, String feedback) {
        this.recipeTitle = new RecipeTitle(recipeTitle);
        this.prepTime = new PrepTime(prepTime);
        this.rating = assignRating(rating);
        this.prepSteps = new PrepSteps(prepSteps);
        this.requiredIngredients = new RequiredIngredients(requiredIngredients);
        this.feedback = new Feedback(feedback);
    }

    public RecipeTitle getRecipeTitle() {
        return this.recipeTitle;
    }

    public PrepTime getPrepTime(String prepTime) { return this.prepTime; }

    public Rating getRating() {
        return this.rating;
    }

    public PrepSteps getPrepSteps() {
        return this.prepSteps;
    }

    public RequiredIngredients getRequiredIngredients() {
        return this.requiredIngredients;
    }

    public ArrayList<Ingredient> getListOfIngredients() {
        return this.requiredIngredients.getList();
    }

    public void editRating(String rating) {
        if (rating.equals("average")) {
            this.rating = Rating.AVERAGE;
        } else if (rating.equals("good")) {
            this.rating = Rating.GOOD;
        } else if (rating.equals("delicious")) {
            this.rating = Rating.DELICIOUS;
        } else {
            this.rating = Rating.UNRATED;
        }
    }

    public void editFeedback(String feedback) {
        this.feedback.edit(feedback);
    }

    public Feedback getFeedback() {
        return this.feedback;
    }

    public void editPrepTime(String prepTime) {
        this.prepTime.edit(prepTime);
    }

    public String toSaveString() {
        return this.recipeTitle.toSaveString().trim() + " | "
                + this.prepTime.toSaveString().trim() + " | "
                + this.checkRating().trim() + " | "
                + this.prepSteps.toSaveString().trim() + " | "
                + this.requiredIngredients.toSaveString().trim() + " | "
                + this.feedback.toSaveString().trim();
    }

    public String getViewString() {
        return LABEL_TITLE + this.recipeTitle.toString() + "\n"
                + "\n"
                + LABEL_PREPTIME + this.prepTime.toString()
                + "\n"
                + LABEL_RATING + this.checkRating() + "\n"
                + "\n"
                + LABEL_PREPSTEPS + this.prepSteps.toViewString()
                + "\n"
                + LABEL_REQ_INGREDIENTS + this.requiredIngredients.toViewString()
                + "\n"
                + LABEL_FEEDBACK + this.feedback.toString();
    }

    public String getViewReqString() {
        return LABEL_REQ_INGREDIENTS + this.requiredIngredients.toViewString();
    }

    private Rating assignRating(String rating) { // can try switch statements too.
        if (rating.equals("Average")) {
            return Rating.AVERAGE;
        } else if (rating.equals("Good")) {
            return Rating.GOOD;
        } else if (rating.equals("Delicious")) {
            return Rating.DELICIOUS;
        } else {
            return Rating.UNRATED;
        }
    }

    private String checkRating() {
        switch (this.rating) {
            case AVERAGE: return "Average";
            case GOOD: return "Good";
            case DELICIOUS: return "Delicious";
            default: return "Unrated";
        }
    }
}