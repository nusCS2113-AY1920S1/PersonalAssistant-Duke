package duke.task.recipetasks;

public class Recipe {

    String recipeTitle;
    Rating rating;
    PrepStep prepStep;
    RequiredIngredients requiredIngredients; // requiredIngredients is a list of recipeIngredient objects.
    Feedback feedback;

    public Recipe(String recipeTitle) {
        this.recipeTitle = recipeTitle;
        this.rating = Rating.UNRATED;
        this.prepStep = new PrepStep();
        this.requiredIngredients = new RequiredIngredients();
        this.feedback = new Feedback();
    }

    public Recipe(String recipeTitle, RequiredIngredients requiredIngredient) {
        this.recipeTitle = recipeTitle;
        this.rating = Rating.UNRATED;
        this.prepStep = new PrepStep();
        this.requiredIngredients = requiredIngredient;
        this.feedback = new Feedback();
    }

    public Recipe(String recipeTitle, String rating, String prepStep, String requiredIngredients, String feedback) {
        this.recipeTitle = recipeTitle;
        this.rating = assignRating(rating);
        this.prepStep = new PrepStep(prepStep);
        this.requiredIngredients = new RequiredIngredients(requiredIngredients);
    }

    public String getRecipeTitle() {
        return this.recipeTitle;
    }

    public Rating getRating() {
        return this.rating;
    }

    public PrepStep getPrepStep() {
        return this.prepStep;
    }

    public RequiredIngredients getRequiredIngredients() {
        return this.requiredIngredients;
    }

    public Feedback getFeedback() {
        return this.feedback;
    }

    public String toSaveString() {
        return this.recipeTitle + " | "
                + checkRating() + " | "
                + this.prepStep.toSaveString() + " | "
                + this.requiredIngredients.toSaveString() + " | "
                + this.feedback.toSaveString();
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

    /*
    private String checkRating() {
        if (this.rating == Rating.AVERAGE) {
            return "Average";
        } else if (this.rating == Rating.GOOD) {
            return "Good";
        } else if (this.rating == Rating.DELICIOUS) {
            return "Delicious";
        } else {
            return "Unrated";
        }
    }
     */

    private String checkRating() {
        switch (this.rating) {
            case AVERAGE: return "Average";
            case GOOD: return "Good";
            case DELICIOUS: return "Delicious";
            default: return "Unrated";
        }
    }
}