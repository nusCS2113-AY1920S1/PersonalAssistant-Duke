package duke.task.ingredienttasks;


public class Ingredient {

    private String ingredientName;
    private int quantity;

    public Ingredient(String ingredientName, int quantity) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getName() {
        return ingredientName;
    }

    public String toSaveString() {
        return ingredientName + " | " + quantity;
    }

    public String toString() {
        return ingredientName + " [" + quantity + "] ";
    }

    /*
    public ingredientType getIngredientType() {
        return ingredientType;
    }
    */

//    private String ingredientName;
//    private double ingredientAmount;
//    private String ingredientWeight;
//
//    public Ingredient (String ingredientName, double ingredientAmount, String ingredientWeight) {
//        this.ingredientName = ingredientName;
//        this.ingredientAmount = ingredientAmount;
//        this.ingredientWeight = ingredientWeight;
//    }
//
//    public String getIngredientName() {
//        return ingredientName;
//    }
//
//    public double getIngredientAmount() {
//        return ingredientAmount;
//    }
//
//    public String getIngredientWeight() {
//        return ingredientWeight;
//    }
//
//    public void setIngredientAmount(double ingredientAmt) {
//        ingredientAmount = ingredientAmt;
//    }
}
