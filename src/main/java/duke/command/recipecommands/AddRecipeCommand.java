package duke.command.recipecommands;

import duke.command.Command;
import duke.command.CommandRecipe;
import duke.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.task.recipetasks.RecipeTitle;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_ADD_RECIPE;
import static duke.common.RecipeMessages.MESSAGE_RECIPE_ADDED;

public class AddRecipeCommand extends Command<RecipeList, Ui, RecipeStorage> { // need to settle the problem of duplicate recipes.

    public AddRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here 7");
        } else if (userInput.trim().charAt(9) == ' ') {
            String[] description = userInput.split("\\s", 3);
            if (description.length == 3) {
                String index = description[1].trim();
                if (isParsableInt(index)) {
                    String title = description[2].trim();
                    RecipeTitle recipeTitle = new RecipeTitle(Integer.parseInt(index), title);
                    recipeList.addRecipe(recipeTitle);
                    recipeStorage.saveFile(recipeList);
                    arrayList.add(MESSAGE_RECIPE_ADDED + "       " + title + "\n" + "Now you have " + recipeList.getSize() + " recipe(s) in the list.");
                } else {
                    arrayList.add(ERROR_MESSAGE_RANDOM);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
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
