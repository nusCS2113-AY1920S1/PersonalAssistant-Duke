package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.*;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

/**
 * Handles the view recipe command.
 */
public class ViewRecipeCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class ViewRecipeCommand.
     *
     * @param userInput input command from user
     */
    public ViewRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the view recipe command to view the content of a specific recipe.
     *
     * @param recipeList    contains the recipe list
     * @param ui             deals with interactions with the user
     * @param recipeStorage deals with loading tasks from the file and saving recipes in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_VIEW_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(10) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (!recipeList.containsRecipe(description)) {
                arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
            } else {
                arrayList.add(MESSAGE_RECIPE_TO_BE_VIEWED);
                arrayList.add(recipeList.viewRecipe(description));
            }
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
