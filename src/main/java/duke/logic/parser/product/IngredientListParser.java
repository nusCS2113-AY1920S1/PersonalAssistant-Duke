package duke.logic.parser.product;

import duke.model.ingredient.Ingredient;
import duke.model.ingredient.IngredientList;

public class IngredientListParser {
    private String inputIngredientList;

    /** Constructs a IngredientListParser with the userInput */
    public IngredientListParser(String inputIngredientList) {
        this.inputIngredientList = inputIngredientList;
    }

    public IngredientList getIngredientList() {
        return new IngredientList() {};
    }

    //todo: add logic
    public void addIngredients(String ingredientName) {
        if (!IngredientList.contains(ingredientName)) {
            //add ingredient;
        }
    }
}
