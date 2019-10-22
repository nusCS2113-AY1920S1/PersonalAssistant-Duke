package duke.command.recipecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.list.recipelist.RecipeTitleList;
import duke.storage.RecipeTitleStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

/**
 * Handles the delete command and inherits all the fields and methods of Command parent class.
 */
public class DeleteRecipeTitleCommand extends Command<RecipeTitleList, Ui, Ui, RecipeTitleStorage> {

    /**
     * Constructor for class DeleteCommand.
     * @param userInput String containing input command from user
     */
    public DeleteRecipeTitleCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeTitleList recipeTitleList, Ui ui, Ui ui1, RecipeTitleStorage recipeTitleStorage) throws DukeException, ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_DELETE_RECIPE_TITLE)) {
            arrayList.add(ERROR_MESSAGE_EMPTY_INDEX + MESSAGE_FOLLOWUP_EMPTY_INDEX);
        } else if (userInput.trim().charAt(5) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (isParsable(description)) {
                //converting string to integer
                int index = Integer.parseInt(description);
                if (index > recipeTitleList.getSize() || index <= 0) {
                    if (recipeTitleList.getSize() == 0) {
                        arrayList.add(ERROR_MESSAGE_EMPTY_LIST);
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_INDEX + recipeTitleList.getSize() + ".");
                    }
                } else {
                    arrayList.add(MESSAGE_RECIPE_DELETED + "         " + recipeTitleList.getRecipeTitleList().get(index - 1));
                    recipeTitleList.deleteIngredient(index - 1);
                    recipeTitleStorage.saveFile(recipeTitleList);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_UNKNOWN_INDEX);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

//    @Override
//    public ArrayList<String> execute(RecipeIngredientList recipeIngredientList, Ui ui, RecipeIngredientStorage recipeIngredientStorage) throws DukeException, ParseException {
//        return null;
//    }

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