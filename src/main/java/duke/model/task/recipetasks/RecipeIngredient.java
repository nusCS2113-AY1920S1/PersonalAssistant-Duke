package duke.model.task.recipetasks;

import duke.model.task.ingredienttasks.UnitOfMeasurement;

public class RecipeIngredient {

    private int recipeIndex;
    private String recipeIngredientName;
    private double recipeIngredientQuantity;
    private UnitOfMeasurement unit;
    private String additionalInfo;

    public RecipeIngredient(String recipeIngredientName, String recipeIngredientQuantity, String unit) {
        // this.recipeIndex = recipeIndex;
        this.recipeIngredientName = recipeIngredientName;
        this.recipeIngredientQuantity = Double.parseDouble(recipeIngredientQuantity);
        this.unit = assignUnit(unit);
    }

    public RecipeIngredient(String recipeIndex, String recipeIngredientName, String recipeIngredientQuantity, String unit, String additionalInfo) {
        this.recipeIndex = Integer.parseInt(recipeIndex);
        this.recipeIngredientName = recipeIngredientName;
        this.recipeIngredientQuantity = Double.parseDouble(recipeIngredientQuantity);
        this.unit = assignUnit(unit);
        this.additionalInfo = additionalInfo;
    }

    private UnitOfMeasurement assignUnit(String unit) {
        switch (unit) {
            case "kg": return UnitOfMeasurement.KG;
            case "ml": return UnitOfMeasurement.ML;
            case "l": return UnitOfMeasurement.L;
            case "cup": return UnitOfMeasurement.CUP;
            case "teaspoon": return UnitOfMeasurement.TEASPOON;
            case "tablespoon": return UnitOfMeasurement.TABLESPOON;
            default: return UnitOfMeasurement.G;
        }
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

    public UnitOfMeasurement getUnit() {
        return this.unit;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public String toSaveString() {
        return recipeIndex + " | " + recipeIngredientName + " | " + recipeIngredientQuantity + " | " + unit + " | " + additionalInfo;
    }

    public String toString() {
        return " [ " + recipeIngredientName + " | " + recipeIngredientQuantity + " | " + unit + " | " + additionalInfo + "] ";
    }
}