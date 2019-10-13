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
            recipeIngredientName = userInput.split("\\s",3)[0].trim();
            recipeIngredientQuantity = userInput.split("\\s",3)[1].trim();
            recipeIngredientWeight = userInput.split("\\s",3)[2].trim();
            System.out.println(recipeIngredientName + "......" + recipeIngredientQuantity + "....." + recipeIngredientWeight);
            if (recipeIngredientName.isEmpty() || recipeIngredientQuantity.isEmpty() || recipeIngredientWeight.isEmpty()) {
                arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
            } else {
                if (isParsable(recipeIngredientQuantity)) {
                    recipeIngredientList.addRecipeIngredient(recipeIngredientName, Integer.parseInt(recipeIngredientQuantity), recipeIngredientWeight);
                    recipeIngredientStorage.saveFile(recipeIngredientList);
                    int index = recipeIngredientList.getSize();
                    System.out.println(index);
                    arrayList.add(MESSAGE_ADDED + "       " + recipeIngredientList.listRecipeIngredients().get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + " tasks in the list");
                } else {
                    arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_QUANTITY);
                }
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    private static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
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
