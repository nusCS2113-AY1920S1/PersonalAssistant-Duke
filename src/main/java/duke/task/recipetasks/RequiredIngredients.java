package duke.task.recipetasks;

import duke.exception.DukeException;

import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.ERROR_MESSAGE_INVALID_RECIPE_FORMAT;

public class RequiredIngredients {

    ArrayList<RecipeIngredient> requiredIngredientList;

    public RequiredIngredients() {
        this.requiredIngredientList = new ArrayList<RecipeIngredient>();
    }

    public RequiredIngredients(String requiredIngredientsFromStorage) throws DukeException {
        this.requiredIngredientList = new ArrayList<RecipeIngredient>();
        parseIngredientsFromStorage(requiredIngredientsFromStorage);
    }

    public void parseIngredientsFromStorage(String description) throws DukeException {

        String recipeIngredientWeight = "";
        String recipeIngredientQuantity = "";
        String recipeIngredientName = "";
        String temp = description.split("q/", 2)[0].trim();
        String remaining = description.split("q/", 2)[1].trim();
        recipeIngredientName = temp.split("\\s", 2)[1].trim();

        if (remaining.contains("w/")) {
            recipeIngredientQuantity = remaining.split("w/")[0].trim();
            recipeIngredientWeight = remaining.split("w/")[1].trim();
            this.requiredIngredientList.add(new RecipeIngredient(recipeIngredientName, recipeIngredientQuantity, recipeIngredientWeight));
        } else {
            throw new DukeException(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
        }
        System.out.println(recipeIngredientName + "......" + recipeIngredientQuantity + "....." + recipeIngredientWeight);
    }

    private static boolean isParsableInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
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
