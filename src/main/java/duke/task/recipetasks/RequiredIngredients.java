package duke.task.recipetasks;

import duke.task.ingredienttasks.Ingredient;

import java.util.ArrayList;

public class RequiredIngredients {

    ArrayList<Ingredient> requiredIngredientList;
    ArrayList<Ingredient> tempList = new ArrayList<>();

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
        System.out.println("this is the value before adding of new ingredient: " + this.requiredIngredientList.toString());
        this.requiredIngredientList.addAll(tempList);
        System.out.println("this is the value after adding of temp list: " + tempList.toString());
        System.out.println("this is the value after adding of temp list: " + this.requiredIngredientList.toString());
        this.requiredIngredientList.add(new Ingredient(recipeIngredientName, quantity, unit, additionalInfo));
    }

    public void parseIngredientsFromStorage(String requiredIngredientsFromStorage) {
        String[] split = requiredIngredientsFromStorage.split("\\|", 2);
        String ingredientName, quantity = null, unit = null, additionalInfo = null, remaining, remaining2;
        if (split.length == 2) {
            ingredientName = split[0].substring(1).trim();
            remaining = split[1].trim();
            String[] split2 = remaining.split("\\|", 2);
            if (split2.length == 2) {
                quantity = split2[0].trim();
                remaining2 = split2[1].trim();
                String[] split3 = remaining2.split("\\|", 2);
                if (split3.length == 2) {
                    unit = split3[0].trim();
                    additionalInfo = split3[1].trim();
                    this.requiredIngredientList.add(new Ingredient(ingredientName, quantity, unit, additionalInfo));
                    tempList.addAll(this.requiredIngredientList);
                    System.out.println("this is the value for ingredient loaded from storage into temp list: " + tempList.toString());
                }
            }
            System.out.println(ingredientName + "...." + quantity + "...." + unit + "...." + additionalInfo);
        }
        System.out.println("this is the value for ingredient loaded from storage: " + this.requiredIngredientList.toString());
    }

    public String toSaveString() {
        String joinedString = "";
        if (requiredIngredientList.isEmpty()) {
            joinedString = "No required ingredient.";
        } else {
            for (Ingredient ingredient : requiredIngredientList) {
                Ingredient temp = requiredIngredientList.get(requiredIngredientList.size() - 1);
                joinedString += ("< " + ingredient.toSaveString() + " >");
                System.out.println("this is the value for inner ingredient: " + ingredient.toSaveString());
            }
        }
        System.out.println("this is the value for inner joinedstring: " + joinedString);
        return joinedString;
    }
}