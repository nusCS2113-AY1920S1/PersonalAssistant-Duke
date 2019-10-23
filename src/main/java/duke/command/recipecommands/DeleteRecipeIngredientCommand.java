package duke.command.recipecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.list.recipelist.RecipeIngredientList;
import duke.storage.RecipeIngredientStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.MESSAGE_FOLLOWUP_EMPTY_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_EMPTY_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_EMPTY_LIST;
import static duke.common.Messages.ERROR_MESSAGE_INVALID_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_UNKNOWN_INDEX;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.RecipeMessages.COMMAND_DELETE_RECIPE_INGREDIENT;
import static duke.common.RecipeMessages.MESSAGE_RECIPE_DELETED;

/**
 * Handles the delete command and inherits all the fields and methods of Command parent class.
 */
public class DeleteRecipeIngredientCommand extends Command<RecipeIngredientList, Ui, RecipeIngredientStorage> {

    /**
     * Constructor for class DeleteCommand.
     * @param userInput String containing input command from user
     */
    public DeleteRecipeIngredientCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeIngredientList recipeIngredientList, Ui ui, RecipeIngredientStorage recipeIngredientStorage) throws DukeException, ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_DELETE_RECIPE_INGREDIENT)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInput.trim().charAt(5) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (isParsable(description)) {
                //converting string to integer
                int index = Integer.parseInt(description);
                if (index > recipeIngredientList.getSize() || index <= 0) {
                    if (recipeIngredientList.getSize() == 0) {
                        arrayList.add(ERROR_MESSAGE_EMPTY_LIST);
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_INDEX + recipeIngredientList.getSize() + ".");
                    }
                } else {
                    arrayList.add(MESSAGE_RECIPE_DELETED + "         " + recipeIngredientList.getRecipeIngredientList().get(index - 1));
                    recipeIngredientList.deleteIngredient(index - 1);
                    recipeIngredientStorage.saveFile(recipeIngredientList);
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