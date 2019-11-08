package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.model.list.recipelist.RecipeList;
import duke.model.task.recipetasks.Recipe;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

/**
 * Handles the delete recipe command.
 */
public class DeleteRecipeCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class DeleteRecipeCommand.
     *
     * @param userInput input command from user
     */
    public DeleteRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the delete recipe command to deletes a specific recipe.
     *
     * @param recipeList    contains the recipe list
     * @param ui             deals with interactions with the user
     * @param recipeStorage deals with loading tasks from the file and saving recipes in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_DELETE_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(12) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            boolean isListEmpty = false;
            if (recipeList.getSize() == 0) {
                isListEmpty = true;
            }
            Recipe value = recipeList.deleteRecipe(description);
            if (!isListEmpty) {
                if (value == null) {
                    arrayList.add(ERROR_MESSAGE_DELETE_RECIPE_NOT_FOUND);
                    recipeStorage.saveFile(recipeList);
                } else {
                    arrayList.add(MESSAGE_RECIPE_DELETED + "       " + description + "\n" + "Now you have " + recipeList.getSize() + " recipe(s) in the list.");
                    recipeStorage.saveFile(recipeList);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_RECIPE_LIST_IS_EMPTY);
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
