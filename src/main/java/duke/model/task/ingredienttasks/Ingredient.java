package duke.model.task.ingredienttasks;
import static duke.common.InventoryMessages.NO_ADDITIONAL_INFO;

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

    public void addMass(String quantity, String unit) {
        // If original unit and new unit is the same, this.unit dont change. Else, this.unit = G
        // if original unit and new unit is not the same,
        // check the base unit, if base unit is already G, convert the new unit to G and the quantity accordingly.
        // if base unit is not G, convert both this.unit and new unit to G and their quantity accordingly.
        double additionalQuantity = Double.parseDouble(quantity);
        UnitOfMeasurement newUnit = assignUnit(unit);
        if (this.unit == newUnit) { // added unit is same as prev
            this.quantity += additionalQuantity;
            this.mass = calculateMass();
        } else { // added unit is different
            if (this.unit == UnitOfMeasurement.G) { // if originally is already in grams.
                additionalQuantity = convertQuantity(additionalQuantity, newUnit);
                this.quantity += additionalQuantity;
                this.mass = calculateMass();
            } else { // if original is not in grams. change it to grams
                this.quantity = convertQuantity(this.quantity, this.unit);
                additionalQuantity = convertQuantity(additionalQuantity, newUnit);
                this.quantity += additionalQuantity;
                this.unit = UnitOfMeasurement.G;
                this.mass = calculateMass();
            }
        }
    }

    public void deductMass(double ingredientMass) {
        this.mass -= ingredientMass;
        System.out.println(this.mass + "left");
    }

    public void updateQuantity() {
        switch (this.unit) {
            case KG:
            case L:
                this.quantity = (this.mass/1000.0);
                break;
            case CUP: this.quantity = (this.mass/237.0);
            break;
            case TEASPOON: this.quantity = (this.mass/5.0);
            break;
            case TABLESPOON: this.quantity = (this.mass/13.0);
            break;
            default: this.quantity = this.mass;
        }
    }

    public double convertQuantity(double quantity, UnitOfMeasurement newUnit) {
        switch (newUnit) {
            case KG:
            case L:
                return quantity*1000;
            case CUP: return quantity*237;
            case TEASPOON: return quantity*5;
            case TABLESPOON: return quantity*13;
            default: return quantity;
        }
    }

    public void replaceInfo(String additionalInfo) {
        if (additionalInfo != NO_ADDITIONAL_INFO) {
            this.additionalInfo = additionalInfo;
        }
    }

    public double getQuantity() {
        return this.quantity;
    }

    public String getIngredientName() {
        return this.ingredientName;
    }

    public String getAdditionalInfo() {
        return this.additionalInfo;
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
        return ingredientName + " , " + quantity + " , " + unit + " , " + additionalInfo;
    }

    public String toString() {
        return ingredientName + " [" + quantity + " | " + unit + " | " + additionalInfo + "] ";
    }
}
