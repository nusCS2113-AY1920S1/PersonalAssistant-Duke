package duke.command.recipecommands;

import duke.command.CommandRecipeTitle;
import duke.list.recipelist.RecipeTitleList;
import duke.storage.RecipeTitleStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_LIST_RECIPES;

/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class ListAllRecipeCommand extends CommandRecipeTitle {

    /**
     * Constructor for class ListCommand.
     * @param userInput String containing input command from user
     */
    public ListAllRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the list command to display all tasks in task list.
     * @param recipeTitleList contains the task list
     * @param ui deals with interactions with the user
     * @param recipeTitleStorage deals with loading tasks from the file and saving tasks in the file
     */

    public ArrayList<String> execute(RecipeTitleList recipeTitleList, Ui ui, RecipeTitleStorage recipeTitleStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_LIST_RECIPES)) {
            arrayList.add(MESSAGE_TASKED);
            arrayList.addAll(recipeTitleList.listRecipeTitle());
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
