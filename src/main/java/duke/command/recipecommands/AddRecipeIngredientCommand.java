package duke.command.recipecommands;

import duke.command.CommandRecipeIngredient;
import duke.list.recipelist.RecipeIngredientList;

import duke.storage.RecipeIngredientStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

public class AddRecipeIngredientCommand extends CommandRecipeIngredient {

    public AddRecipeIngredientCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeIngredientList recipeIngredientList, Ui ui, RecipeIngredientStorage recipeIngredientStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        String recipeIngredientWeight = "";
        String recipeIngredientQuantity = "";
        String recipeIngredientName = "";
        if (userInput.trim().equals(COMMAND_ADD_RECIPE_INGREDIENT)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here1");
        } else if (userInput.trim().charAt(19) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (description.contains("q/")) {
                recipeIngredientName = description.split("q/",2)[0].trim();
                String remaining = description.split("q/", 2)[1].trim();
                if (remaining.contains("w/")) {
                    recipeIngredientQuantity = remaining.split("w/")[0].trim();
                    recipeIngredientWeight = remaining.split("w/")[1].trim();
                    if (isParsable(recipeIngredientQuantity)) {
                        recipeIngredientList.addRecipeIngredient(recipeIngredientName, Double.parseDouble(recipeIngredientQuantity), recipeIngredientWeight);
                        recipeIngredientStorage.saveFile(recipeIngredientList);
                        int index = recipeIngredientList.getSize();
                        System.out.println(index);
                        arrayList.add(MESSAGE_ADDED + "       " + recipeIngredientList.listRecipeIngredients().get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + " ingredients in the list");
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_QUANTITY);
                    }
                } else {
                    arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
            }
            System.out.println(recipeIngredientName + "......" + recipeIngredientQuantity + "....." + recipeIngredientWeight);
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    private static boolean isParsable(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
