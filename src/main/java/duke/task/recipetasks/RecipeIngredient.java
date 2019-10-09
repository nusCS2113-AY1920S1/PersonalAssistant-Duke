package duke.task.recipetasks;

public class RecipeIngredient {

    private String ingredientName;
    private int quantity;

    public RecipeIngredient(String ingredientName, int num) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public String getIngredient() {
        return ingredientName;
    }

    public String toSaveString() {
        return ingredientName + " | " + quantity;
    }

    public String toString() {
        return ingredientName + "[" + quantity + "] ";
    }

}