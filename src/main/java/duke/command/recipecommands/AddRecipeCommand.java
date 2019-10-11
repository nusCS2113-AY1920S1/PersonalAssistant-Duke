package duke.command.recipecommands;

import duke.command.CommandRecipe;
import duke.exception.DukeException;
import duke.list.recipelist.RecipeList;
import duke.model.recipe.Recipe;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.util.ArrayList;

import static duke.common.IngredientMessages.ERROR_MESSAGE_INVALID_FORMAT;
import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_ADD_RECIPE;

public class AddRecipeCommand extends CommandRecipe {

    public AddRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> feedback(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws DukeException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here1");
        } else if (userInput.trim().charAt(9) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (description.contains("/")) {
                String[] parts = description.split("/", 4);
                String recipeTitle = parts[1];
                recipe.addRecipe(recipeTitle, "hvnt implement", "hvnt implement", "hvnt implement");
                recipeIngredientStorage.saveFile(recipeIngredientList);
                int index = recipeIngredientList.getSize();
                System.out.println(index);
                arrayList.add(MESSAGE_ADDED + "       " + recipeIngredientList.listRecipeIngredients().get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + " tasks in the list");
            } else {
                arrayList.add(ERROR_MESSAGE_INVALID_FORMAT);
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
