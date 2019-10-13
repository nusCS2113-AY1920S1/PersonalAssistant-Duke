package duke.task.recipetasks;

public class RecipeIngredient {
    private String recipeIngredientName;
    private int recipeIngredientQuantity;
    private String recipeIngredientWeight;

    public RecipeIngredient(String recipeIngredientName, int recipeIngredientQuantity, String recipeIngredientWeight) {
        this.recipeIngredientName = recipeIngredientName;
        this.recipeIngredientQuantity = recipeIngredientQuantity;
        this.recipeIngredientWeight = recipeIngredientWeight;
    }

    public int getRecipeIngredientQuantity() {
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