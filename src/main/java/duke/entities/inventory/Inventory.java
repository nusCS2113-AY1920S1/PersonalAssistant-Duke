package duke.entities.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import duke.entities.Ingredient;

public class Inventory extends Ingredient {
    private int quantity;

    public Inventory() {

    }

    public Inventory(@JsonProperty("ingredientName") String ingredientName,
                 @JsonProperty("ingredientCost") double ingredientCost,
                 @JsonProperty("ingredientQuantity") int ingredientQuantity) {
        super.setName(ingredientName);
        super.setCost(ingredientCost);
        this.quantity = ingredientQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
