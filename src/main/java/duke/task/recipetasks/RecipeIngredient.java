package duke.task.recipetasks;

import duke.task.ingredienttasks.Ingredient;

public class RecipeIngredient extends Ingredient {

    public RecipeIngredient(String ingredientName, int quantity) {
        super(ingredientName, quantity);
    }

    public int getQuantity() {
        return super.getQuantity();
    }

    public String getName() {
        return super.getName();
    }

    public String toSaveString() {
        return super.toSaveString();
    }

    public String toString() {
        return super.toString();
    }

}