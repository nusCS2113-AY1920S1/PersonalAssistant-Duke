package duke.task.recipetasks;

import duke.task.ingredienttasks.Ingredient;

import java.util.ArrayList;

public class RequiredIngredients {

    ArrayList<Ingredient> requiredIngredientList;

    public RequiredIngredients() {
        this.requiredIngredientList = new ArrayList<Ingredient>();
    }

    public RequiredIngredients(String requiredIngredientsFromStorage) {
        this.requiredIngredientList = new ArrayList<Ingredient>();
        parseIngredientsFromStorage(requiredIngredientsFromStorage);
    }

    public RequiredIngredients(String recipeIngredientName, String quantity, String unit, String additionalInfo) {
        this.requiredIngredientList = new ArrayList<Ingredient>();
        parseIngredient(recipeIngredientName, quantity, unit, additionalInfo);
    }

    public void parseIngredient(String recipeIngredientName, String quantity, String unit, String additionalInfo) {
        this.requiredIngredientList.add(new Ingredient(recipeIngredientName, quantity, unit, additionalInfo));
    }

    public void parseIngredientsFromStorage(String requiredIngredientsFromStorage) {
        String[] split = requiredIngredientsFromStorage.split("\\|", 2);
        String ingredientName, quantity, unit, additionalInfo, remaining, remaining2;
        if (split.length == 2) {
            ingredientName = split[0];
            remaining = split[1];
            String[] split2 = remaining.split("\\|", 2);
            if (split2.length == 2) {
                quantity = split2[0];
                remaining2 = split2[1];
                String[] split3 = remaining2.split("\\|", 2);
                if (split3.length == 2) {
                    unit = split3[0];
                    additionalInfo = split3[1];
                    this.requiredIngredientList.add(new Ingredient(ingredientName, quantity, unit, additionalInfo));
                }
                /*
                else if (split3.length == 1) {
                    unit = split3[0];
                    additionalInfo = "No additional information.";
                    this.requiredIngredientList.add(new Ingredient(ingredientName, quantity, unit, additionalInfo));
                }
                 */
            }
        }
    }

    public String toSaveString() {
        String joinedString = "";
        if (requiredIngredientList.isEmpty()) {
            joinedString = "No required ingredient.";
        }
        for (Ingredient Ingredient : requiredIngredientList) {
            String.join(Ingredient.toSaveString(), joinedString);
            String.join(" | ", joinedString);
        }
        return joinedString;
    }
}