package duke.task.recipetasks;

public class RecipeIngredient {
    private String recipeIngredientName;
    private double recipeIngredientQuantity;
    private String recipeIngredientWeight;

    public RecipeIngredient(String recipeIngredientName, double recipeIngredientQuantity, String recipeIngredientWeight) {
        this.recipeIngredientName = recipeIngredientName;
        this.recipeIngredientQuantity = recipeIngredientQuantity;
        this.recipeIngredientWeight = recipeIngredientWeight;
    }

    public double getRecipeIngredientQuantity() {
        return recipeIngredientQuantity;
    }

    public String getRecipeIngredientName() {
        return recipeIngredientName;
    }

    public String getRecipeIngredientWeight() {
        return recipeIngredientWeight;
    }

    public String toSaveString() {
        return recipeIngredientName + " | " + recipeIngredientQuantity + " | " + recipeIngredientWeight;
    }

    public String toString() {
        return recipeIngredientName + "[" + recipeIngredientQuantity + " " + recipeIngredientWeight + "] ";
    }
}