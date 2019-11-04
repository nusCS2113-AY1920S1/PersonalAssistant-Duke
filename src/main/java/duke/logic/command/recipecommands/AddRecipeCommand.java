package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

public class AddRecipeCommand extends Command<RecipeList, Ui, RecipeStorage> { // need to settle the problem of duplicate recipes.

    /**
     * Constructor for class AddRecipeCommand.
     *
     * @param userInput input command from user
     */
    public AddRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(9) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (recipeList.containsRecipe(description)) {
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
