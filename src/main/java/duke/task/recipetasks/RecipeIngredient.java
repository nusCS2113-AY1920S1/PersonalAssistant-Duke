package duke.task.recipetasks;

public class RecipeIngredient {

    private int recipeIndex;
    private String recipeIngredientName;
    private double recipeIngredientQuantity;
    private String recipeIngredientWeight;

    public RecipeIngredient(int recipeIndex, String recipeIngredientName, double recipeIngredientQuantity, String recipeIngredientWeight) {
        this.recipeIndex = recipeIndex;
        this.recipeIngredientName = recipeIngredientName;
        this.recipeIngredientQuantity = recipeIngredientQuantity;
        this.recipeIngredientWeight = recipeIngredientWeight;
    }

    public int getRecipeIngredientIndex() {
        return recipeIndex;
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
        return recipeIndex + " | " + recipeIngredientName + " | " + recipeIngredientQuantity + " | " + recipeIngredientWeight;
    }

    public String toString() {
        return "[" + recipeIndex + "] " + recipeIngredientName + "[" + recipeIngredientQuantity + " " + recipeIngredientWeight + "] ";
    }
}