package duke.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.model.inventory.Ingredient;

public class JsonAdaptedIngredient {
    private final String name;
    private final Double unitPrice;
    private final String remarks;

    @JsonCreator
    public JsonAdaptedIngredient(
            @JsonProperty("name") String name,
            @JsonProperty("unitPrice") Double unitPrice,
            @JsonProperty("remarks") String remarks) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.remarks = remarks;
    }

    public JsonAdaptedIngredient(Ingredient source) {
        this.name = source.name;
        this.unitPrice = source.unitPrice;
        this.remarks = source.remarks;
    }

    public Ingredient toModelType() {
        return new Ingredient(name, unitPrice, remarks);
    }
}
