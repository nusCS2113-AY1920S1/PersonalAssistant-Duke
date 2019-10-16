package duke.command.inventorycommands;

import duke.command.CommandIngredients;
import duke.list.ingredientlist.IngredientList;
import duke.storage.IngredientStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.IngredientMessages.MESSAGE_LIST_INGREDIENTS;
import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;

/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class ListIngredientsCommand extends CommandIngredients {

    /**
     * Constructor for class ListCommand.
     * @param userInput String containing input command from user
     */
    public ListIngredientsCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the list command to display all tasks in task list.
     * @param ingredientList contains the task list
     * @param ui deals with interactions with the user
     * @param ingredientStorage deals with loading tasks from the file and saving tasks in the file
     */

    public ArrayList<String> execute(IngredientList ingredientList, Ui ui, IngredientStorage ingredientStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(MESSAGE_LIST_INGREDIENTS);
        for (int i = 0; i < ingredientList.getSize(); i++) {
            final int displayedIndex = i + DISPLAYED_INDEX_OFFSET;
            arrayList.add("     " + displayedIndex + ". " + ingredientList.get(i));
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
