package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

/**
 * Handles the add new recipe command.
 */
public class AddRecipeCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class AddRecipeCommand.
     *
     * @param userInput input command from user
     */
    public AddRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Validates the input to be alphabets.
     *
     * @param input String input from user
     * @return true if the string consist only alphabets and false otherwise
     */
    private static boolean isValidRecipeTitle(String input) {
        for (char c : input.toCharArray()) {
            if (!Character.isLetter(c) && !(c == ' ')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Processes the add command to add a new recipe into recipe list.
     *
     * @param recipeList    contains the recipe list
     * @param ui             deals with interactions with the user
     * @param recipeStorage deals with loading tasks from the file and saving recipes in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(9) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (!isValidRecipeTitle(description)) {
                arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_TITLE);
            } else if (recipeList.containsRecipe(description)) {
                arrayList.add(ERROR_MESSAGE_RECIPE_ALREADY_EXISTS);
            } else {
                recipeList.addRecipe(description);
                recipeStorage.saveFile(recipeList);
                arrayList.add(MESSAGE_RECIPE_ADDED + "       " + description + "\n" + "Now you have " + recipeList.getSize() + " recipe(s) in the list.");
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
