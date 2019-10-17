package duke.command.recipecommands;

import duke.command.CommandRecipeIngredient;
import duke.command.CommandRecipeTitle;
import duke.exception.DukeException;
import duke.list.recipelist.RecipeIngredientList;
import duke.list.recipelist.RecipeTitleList;
import duke.storage.RecipeIngredientStorage;
import duke.storage.RecipeTitleStorage;
import duke.task.recipetasks.RecipeIngredient;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_LIST_RECIPE_INGREDIENT;
import static duke.common.RecipeMessages.ERROR_MESSAGE_INVALID_RECIPE_INDEX;

/**
 * Handles the list command and inherits all the fields and methods of Command parent class.
 */
public class ListRecipeIngredientCommand extends CommandRecipeTitle<RecipeIngredientList, Ui, RecipeIngredientStorage> {

    /**
     * Constructor for class ListCommand.
     * @param userInput String containing input command from user
     */
    public ListRecipeIngredientCommand(String userInput) {
        this.userInput = userInput;
    }

//    @Override
//    public ArrayList<String> execute(RecipeTitleList recipeTitleList, Ui ui, RecipeTitleStorage recipeTitleStorage) throws DukeException, ParseException {
//        return null;
//    }

    public ArrayList<String> execute(RecipeIngredientList recipeIngredientList, Ui ui, RecipeIngredientStorage recipeIngredientStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_LIST_RECIPE_INGREDIENT)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here1");
        } else if (userInput.trim().charAt(14) == ' ') {
            String index = userInput.split("\\s", 2)[1];
            if (isParsableInt(index)) {
                arrayList.add(MESSAGE_TASKED);
                for (RecipeIngredient recipeIngredient : recipeIngredientList.getRecipeIngredientList()) {
                    if (recipeIngredient.getRecipeIngredientIndex() == Integer.parseInt(index)) {
                        arrayList.add(recipeIngredient.toString());
                    }
                }
            } else {
                arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_INDEX);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    private static boolean isParsableInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
