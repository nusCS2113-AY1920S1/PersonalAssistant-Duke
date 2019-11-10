package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.ui.Ui;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.model.task.recipetasks.Recipe;

import java.util.*;

import static duke.common.Messages.DISPLAYED_INDEX_OFFSET;
import static duke.common.Messages.ERROR_MESSAGE_RANDOM;
import static duke.common.RecipeMessages.*;

/**
 * Handles the list all recipe command.
 */
public class ListAllRecipeCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class ListAllRecipeCommand.
     *
     * @param userInput input command from user
     */
    public ListAllRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the list recipe command to show a list of the existing recipes in the recipe list.
     *
     * @param recipeList    contains the recipe list
     * @param ui             deals with interactions with the user
     * @param recipeStorage deals with loading tasks from the file and saving recipes in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_LIST_RECIPES)) {
            arrayList.add(MESSAGE_HERE_ARE_THE_RECIPES);
            // using generics. can use KeySet also but use EntrySet can extend for further purpose later on.
            arrayList.addAll(listOfRecipes(recipeList.getRecipeList()));
        } else if (userInput.trim().equals(COMMAND_LIST_RECIPES_BY_PREPTIME)) {
            arrayList.add(MESSAGE_HERE_ARE_THE_RECIPES);
            arrayList.addAll(listOfRecipesyPrepTime(recipeList.getRecipeList()));
        }
            else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Processes the list command to display all recipes in recipe list in the order that they are entered.
     *
     * @param map the recipe list
     * @return arrayList containing all the recipes
     */
    private ArrayList<String> listOfRecipes(TreeMap<String, Recipe> map) {
        Set entries = map.entrySet();
        Iterator entryIter = entries.iterator();
        ArrayList<String> arrayList = new ArrayList<>();
        int i = 0;
        while (entryIter.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIter.next();
            Recipe value = (Recipe) entry.getValue();  // Get the value.
            arrayList.add((i + DISPLAYED_INDEX_OFFSET) + ". " + value.getRecipeTitle().getTitle());
            i++;
        }
        return arrayList;
    }

    /**
     * Processes the list command to display all recipes in recipe list in the order of preparation time.
     *
     * @param map the recipe list
     * @return arrayList containing all the recipes in th eorder of preparation time
     */
    private ArrayList<String> listOfRecipesyPrepTime(TreeMap<String, Recipe> map) {
        Set entries = map.entrySet();
        Iterator entryIter = entries.iterator();
        ArrayList<String> arrayList = new ArrayList<>();
        int i = 0;
//        while (entryIter.hasNext()) {
//            Map.Entry entry = (Map.Entry) entryIter.next();
//            Object key = entry.getKey();  // Get the key from the entry.
//            Recipe value = (Recipe) entry.getValue();  // Get the value.
//            arrayList.add(Integer.toString(i + DISPLAYED_INDEX_OFFSET) + ". " + value.getRecipeTitle().getTitle());
//            i++;
//        }
        return arrayList;
    }
}
