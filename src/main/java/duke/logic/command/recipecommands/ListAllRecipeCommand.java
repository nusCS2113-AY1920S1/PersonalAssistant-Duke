package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.ui.Ui;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.model.task.recipetasks.Recipe;

import java.util.*;

import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.RecipeMessages.COMMAND_LIST_RECIPES;
import static duke.common.RecipeMessages.MESSAGE_HERE_ARE_THE_RECIPES;

/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class ListAllRecipeCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class ListCommand.
     * @param userInput String containing input command from user
     */
    public ListAllRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the list command to display all tasks in task list.
     * @param recipeList contains the task list
     * @param recipeStorage deals with loading tasks from the file and saving tasks in the file
     */

    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) {
        System.out.println("went into execute list all recipes");
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_LIST_RECIPES)) {
            arrayList.add(MESSAGE_HERE_ARE_THE_RECIPES);
            // using generics. can use KeySet also but use EntrySet can extend for further purpose later on.
            arrayList.addAll(listOfRecipes(recipeList.getRecipeList()));
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    private ArrayList<String> listOfRecipes(HashMap<Integer, Recipe> map) {
        Set entries = map.entrySet();
        Iterator entryIter = entries.iterator();
        ArrayList<String> arrayList = new ArrayList<>();
        while (entryIter.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIter.next();
            Object key = entry.getKey();  // Get the key from the entry.
            Recipe value = (Recipe) entry.getValue();  // Get the value.
            arrayList.add("     " + key + " " + value.getRecipeTitle());
        }
        return arrayList;
    }
}
