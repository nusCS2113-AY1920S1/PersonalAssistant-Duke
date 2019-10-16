package duke.command.inventorycommands;

import duke.command.CommandIngredients;
import duke.exception.DukeException;
import duke.list.ingredientlist.IngredientList;
import duke.storage.IngredientStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.IngredientMessages.*;
import static duke.common.Messages.*;

/**
 * Handles the delete command and inherits all the fields and methods of Command parent class.
 */
public class DeleteIngredientCommand extends CommandIngredients {

    /**
     * Constructor for class DeleteCommand.
     * @param userInput String containing input command from user
     */
    public DeleteIngredientCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the delete command to delete task in the task list.
     * @param ingredientList contains the task list
     * @param ui deals with interactions with the user
     * @param ingredientStorage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input
     *                      or user inputs an invalid index or the list of tasks is empty
     */
    @Override
    public ArrayList<String> execute(IngredientList ingredientList, Ui ui, IngredientStorage ingredientStorage) throws DukeException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_DELETE_INGREDIENT)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInput.trim().charAt(19) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (isParsable(description)) {
                //converting string to integer
                int index = Integer.parseInt(description);
                if (index > ingredientList.getSize() || index <= 0) {
                    if (ingredientList.getSize() == 0) {
                        arrayList.add(ERROR_MESSAGE_DELETING_FROM_EMPTY_LIST);
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_INDEX + ingredientList.getSize() + ".");
                    }
                } else {
                    ingredientStorage.saveFile(ingredientList);
                    String msg = "";
                    if (ingredientList.getSize() - 1 <= 1) {
                        msg = " ingredient in the list.";
                    } else {
                        msg = MESSAGE_ITEMS2;
                    }
                    arrayList.add(MESSAGE_INGREDIENT_DELETED + "       " + ingredientList.get(index - 1)
                            + "\n" + MESSAGE_ITEMS1 + (ingredientList.getSize() - 1) + msg);
                    ingredientList.deleteIngredient(index - 1);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    /**
     * Validates that user inputs an integer value for the index.
     * @param input String containing integer input from user for the index
     * @return true if the user inputs an integer and false otherwise
     */
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