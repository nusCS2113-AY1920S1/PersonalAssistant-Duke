package duke.model.recipe;

public class Ingredient {
    private String ingredientName;
    private double ingredientAmount;
    private String ingredientWeight;

    public Ingredient (String ingredientName, double ingredientAmount, String ingredientWeight) {
        this.ingredientName = ingredientName;
        this.ingredientAmount = ingredientAmount;
        this.ingredientWeight = ingredientWeight;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public double getIngredientAmount() {
        return ingredientAmount;
    }

    public String getIngredientWeight() {
        return ingredientWeight;
    }

    public void setIngredientAmount(double ingredientAmt) {
        ingredientAmount = ingredientAmt;
    }
}
