package duke.command.recipecommands;

import duke.command.CommandRecipeIngredient;
import duke.list.recipelist.RecipeIngredientList;

import duke.storage.RecipeIngredientStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.IngredientMessages.*;
import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_ADD_RECIPE_INGREDIENTS;

public class AddRecipeIngredientCommand extends CommandRecipeIngredient {

    public AddRecipeIngredientCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> feedback(RecipeIngredientList recipeIngredientList, Ui ui, RecipeIngredientStorage recipeIngredientStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_RECIPE_INGREDIENTS)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here1");
        } else if (userInput.trim().charAt(20) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("q/")) {
                String ingredientName = description.split("q/", 2)[0].trim();
                String quantity = description.split("q/", 2)[1].trim();
                if (ingredientName.isEmpty() || quantity.isEmpty()) {
                    System.out.println("stuck here");
                    arrayList.add(ERROR_MESSAGE_INCOMPLETE);
                } else {
                    if (isParsable(quantity)) {
                        recipeIngredientList.addRecipeIngredient(ingredientName, Integer.parseInt(quantity));
                        recipeIngredientStorage.saveFile(recipeIngredientList);
                        int index = recipeIngredientList.getSize();
                        System.out.println(index);
                        arrayList.add(MESSAGE_ADDED + "       " + recipeIngredientList.listRecipeIngredients().get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + " tasks in the list");
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_QUANTITY);
                    }
                }
            } else {
                arrayList.add(ERROR_MESSAGE_INVALID_FORMAT);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

//    public void execute(IngredientList ingredientList, Ui ui, IngredientStorage ingredientStorage) throws DukeException, ParseException {
//        if (userInput.trim().equals(COMMAND_ADD_INGREDIENT)) {
//            throw new DukeException(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
//        } else if (userInput.trim().charAt(13) == ' ') {
//            String description = userInput.split("\\s",2)[1].trim();
//            if (description.contains("q/")) {
//                String ingredientName = description.split("q/", 2)[0].trim();
//                String quantity = description.split("q/", 2)[1].trim();
//                if (ingredientName.isEmpty() || quantity.isEmpty()) {
//                    throw new DukeException(ERROR_MESSAGE_INCOMPLETE);
//                } else if (isParsable(quantity)) {
//                    ingredientList.addIngredient(ingredientName, Integer.parseInt(quantity));
//                    ingredientStorage.saveFile(ingredientList);
//                }
//            }
//        } else {
//            throw new DukeException(ERROR_MESSAGE_RANDOM);
//        }
//    }

    public static boolean isParsable(String input) {
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
    };
}
