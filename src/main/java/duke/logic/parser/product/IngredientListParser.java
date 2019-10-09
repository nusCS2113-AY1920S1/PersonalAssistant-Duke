package duke.logic.parser.product;

import duke.logic.parser.exceptions.ParseException;
import duke.model.ingredient.Ingredient;
import duke.model.ingredient.IngredientList;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IngredientListParser {
    private String inputIngredientList;

    private static final Pattern FORMAT_INGREDIENT_INPUT = Pattern.compile("((?:\\[)([a-zA-Z0-9_ ]*),([a-zA-Z0-9_ ]*)(?:\\]))");
    private static Matcher matcher;

    /** Constructs a IngredientListParser with the userInput */
    public IngredientListParser(String inputIngredientList) {
        this.inputIngredientList = inputIngredientList;
        matcher = FORMAT_INGREDIENT_INPUT.matcher(inputIngredientList.trim());
    }



    public ArrayList<String> getNames() {
        if (!matcher.matches()) {
            throw new ParseException("");
        }


    }

    public ArrayList<String> getDescription() {

    }

    public IngredientList getIngredientList() {
        return new IngredientList() {};
    }

    //todo: add logic
    public void addIngredients(String ingredientName) {
        if (!IngredientList.contain(ingredientName)) {
            //add ingredient;
        }
    }
}
