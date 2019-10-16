package duke.command.inventorycommands;

import duke.command.CommandIngredients;
import duke.list.ingredientlist.IngredientList;
import duke.storage.IngredientStorage;

import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.IngredientMessages.*;
import static duke.common.Messages.*;

public class AddIngredientCommand extends CommandIngredients {

    public AddIngredientCommand(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.INGREDIENT;
    }

    @Override
    public ArrayList<String> execute(IngredientList ingredientList, Ui ui, IngredientStorage ingredientStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_INGREDIENT)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here1");
        } else if (userInput.trim().charAt(14) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("q/")) {
                String ingredientName = description.split("q/", 2)[0].trim();
                String quantity = description.split("q/", 2)[1].trim();
                if (ingredientName.isEmpty() || quantity.isEmpty()) {
                    System.out.println("stuck here");
                    arrayList.add(ERROR_MESSAGE_INCOMPLETE);
                } else {
                    if (isParsable(quantity)) {
                        ingredientList.addIngredient(ingredientName, Integer.parseInt(quantity));
                        ingredientStorage.saveFile(ingredientList);
                        int index = ingredientList.getSize();
                        System.out.println(index);
                        arrayList.add(MESSAGE_ADDED + "       " + ingredientList.listOfIngredients().get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + " tasks in the list");
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_QUANTITY);
                    }
                }
            } else {
                arrayList.add(ERROR_MESSAGE_ADD_INCORRECT_FORMAT);
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
