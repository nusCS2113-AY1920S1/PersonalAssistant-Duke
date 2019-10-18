package duke.command.recipecommands;

import duke.command.CommandRecipe;
import duke.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.task.recipetasks.Recipe;
import duke.task.recipetasks.RecipeTitle;

import java.util.*;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.RecipeMessages.COMMAND_LIST_RECIPES;
import static duke.common.RecipeMessages.MESSAGE_HERE_ARE_THE_RECIPES;

/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class ListAllRecipeCommand extends CommandRecipe {

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

    public ArrayList<String> execute(RecipeList recipeList, RecipeStorage recipeStorage) {
        System.out.println("went into execute");
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

    private ArrayList<String> listOfRecipes(LinkedHashMap<RecipeTitle, Recipe> map) {
        Set entries = map.entrySet();
        Iterator entryIter = entries.iterator();
        ArrayList<String> arrayList = new ArrayList<>();
        int i = 0;
        while (entryIter.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIter.next();
            Object key = entry.getKey();  // Get the key from the entry.
            Recipe value = (Recipe) entry.getValue();  // Get the value.
            arrayList.add(Integer.toString(i + DISPLAYED_INDEX_OFFSET) + ". " + value.getRecipeTitle().getTitle());
            i++;
        }
        return arrayList;
    }
}
