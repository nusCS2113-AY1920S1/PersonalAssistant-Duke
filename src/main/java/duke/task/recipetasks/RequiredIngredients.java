package duke.task.recipetasks;

import java.util.ArrayList;

public class RequiredIngredients {

    ArrayList<RecipeIngredient> requiredIngredientList;

    public RequiredIngredients() {
        this.requiredIngredientList = new ArrayList<RecipeIngredient>();
    }

    public RequiredIngredients(String requiredIngredientsFromStorage) {
        this.requiredIngredientList = new ArrayList<RecipeIngredient>();
        parseIngredientsFromStorage(requiredIngredientsFromStorage);
    }

    public void parseIngredientsFromStorage(String requiredIngredientsFromStorage) {
        String[] split = requiredIngredientsFromStorage.split("/", 2);
        if (split.length == 2) {
            String name, quantity, weight, remaining1, remaining2;
            String ingredient = split[0];
            remaining1 = split[1];
            name = remaining1.split("\\|", 2)[0];
            remaining2 = remaining1.split("\\|", 2)[1];
            quantity = remaining2.split("\\|", 2)[0];
            weight = remaining2.split("\\|", 2)[1];
            this.requiredIngredientList.add(new RecipeIngredient(name, quantity, weight));
        }
    }

    public String toSaveString() {
        String joinedString = "";
        if (requiredIngredientList.isEmpty()) {
            joinedString = "No required ingredient.";
        }
        for (RecipeIngredient recipeIngredient : requiredIngredientList) {
            String.join(recipeIngredient.toSaveString(), joinedString);
            String.join(" / ", joinedString);
        }
        return joinedString;
    }
}
