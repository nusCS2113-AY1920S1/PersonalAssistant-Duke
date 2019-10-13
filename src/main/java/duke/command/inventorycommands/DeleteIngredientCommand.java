package duke.command.inventorycommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.list.ingredientlist.IngredientList;
import duke.storage.IngredientStorage;
import duke.ui.Ui;

import static duke.common.Messages.MESSAGE_FOLLOWUP_EMPTY_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_EMPTY_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_EMPTY_LIST;
import static duke.common.Messages.ERROR_MESSAGE_INVALID_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_UNKNOWN_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.IngredientMessages.COMMAND_DELETE_INGREDIENT;

/**
 * Handles the delete command and inherits all the fields and methods of Command parent class.
 */
public class DeleteIngredientCommand extends Command {

    /**
     * Constructor for class DeleteCommand.
     * @param userInput String containing input command from user
     */
    public DeleteIngredientCommand(String userInput) {
        this.userInput = userInput;
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

    /**
     * Processes the delete command to delete task in the task list.
     * @param ingredientList contains the task list
     * @param ui deals with interactions with the user
     * @param ingredientStorage deals with loading tasks from the file and saving tasks in the file
     * @throws DukeException if Duke cannot recognize the user input
     *                      or user inputs an invalid index or the list of tasks is empty
     */
    @Override
    public void execute(IngredientList ingredientList, Ui ui, IngredientStorage ingredientStorage) throws DukeException {
        if (userInput.trim().equals(COMMAND_DELETE_INGREDIENT)) {
            throw new DukeException(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInput.trim().charAt(6) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (isParsable(description)) {
                //converting string to integer
                int index = Integer.parseInt(description);
                if (index > ingredientList.getSize() || index <= 0) {
                    if (ingredientList.getSize() == 0) {
                        throw new DukeException(ERROR_MESSAGE_EMPTY_LIST);
                    } else {
                        throw new DukeException(ERROR_MESSAGE_INVALID_INDEX + ingredientList.getSize() + ".");
                    }
                } else {
                    ingredientList.deleteIngredient(index - 1);
                    ingredientStorage.saveFile(ingredientList);
                }
            } else {
                throw new DukeException(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            throw new DukeException(ERROR_MESSAGE_RANDOM);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}