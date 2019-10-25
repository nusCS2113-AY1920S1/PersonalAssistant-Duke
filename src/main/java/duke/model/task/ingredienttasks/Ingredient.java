package duke.model.task.ingredienttasks;


public class Ingredient {

    private String ingredientName;
    private double quantity;
    private UnitOfMeasurement unit;
    private String additionalInfo;
    private double mass; // base in grams.

    public Ingredient(String ingredientName, String quantity, String unit, String additionalInfo) {
        this.ingredientName = ingredientName;
        this.quantity = Double.parseDouble(quantity);
        this.unit = assignUnit(unit);
        this.additionalInfo = additionalInfo;
        this.mass = calculateMass();
    }

    public double getQuantity() {
        return this.quantity;
    }

    public String getIngredientName() {
        return this.ingredientName;
    }

    public UnitOfMeasurement getUnit() {
        return this.unit;
    }

    public double getMass() {
        return this.mass;
    }

    private double calculateMass() { // can remove magic numbers.
        switch (this.unit) {
            case KG:
            case L:
                return this.quantity*1000;
            case CUP: return this.quantity*237;
            case TEASPOON: return this.quantity*5;
            case TABLESPOON: return this.quantity*13;
            default: return this.quantity;
        }
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

    public String toSaveString() {
        return ingredientName + " | " + quantity + " | " + unit + " | " + additionalInfo;
    }

    public String toString() {
        return ingredientName + " [" + quantity + " | " + unit + " | " + additionalInfo + "] ";
    }
}
