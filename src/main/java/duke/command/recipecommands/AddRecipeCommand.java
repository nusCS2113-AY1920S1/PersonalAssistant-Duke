package duke.command.recipecommands;

import duke.command.CommandRecipe;
import duke.exception.DukeException;
import duke.list.recipelist.RecipeList;
import duke.model.recipe.Recipe;
import duke.storage.RecipeStorage;
import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;
import duke.ui.Ui;
import org.javatuples.Triplet;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.IngredientMessages.ERROR_MESSAGE_INCOMPLETE;
import static duke.common.IngredientMessages.ERROR_MESSAGE_INVALID_FORMAT;
import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_ADD_RECIPE;

public class AddRecipeCommand extends CommandRecipe {

    public AddRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> feedback(RecipeList recipeList, RecipeIngredient recipeIngredient,
                                      Rating rating, Feedback feedback, Ui ui, RecipeStorage recipeStorage) throws DukeException, ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        Triplet<RecipeIngredient, Rating, Feedback> recipe = new Triplet<>(recipeIngredient, rating, feedback);
        if (userInput.trim().equals(COMMAND_ADD_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here1");
        } else if (userInput.trim().charAt(9) == ' ') {
            String[] temp = userInput.split("n/",5);
            String recipeTitle = userInput.split("n/",2)[1].trim();
            recipeList.addRecipe(recipeTitle, recipe);
            recipeStorage.saveFile(recipeList);
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
