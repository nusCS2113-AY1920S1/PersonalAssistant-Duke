package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

/**
 * Handles the edit rating command.
 */
public class EditRatingCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class EditRatingCommand.
     *
     * @param userInput input command from user
     */
    public EditRatingCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the edit rating command to edit the rating for a specific recipe.
     *
     * @param recipeList    contains the recipe list
     * @param ui             deals with interactions with the user
     * @param recipeStorage deals with loading tasks from the file and saving recipes in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_EDIT_RATING)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(10) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (description.contains("r/")) {
                String recipeTitle, rating;
                String[] split = description.split("r/", 2);
                recipeTitle = split[0].trim();
                rating = split[1].trim();
                if (recipeTitle.isEmpty()) {
                    arrayList.add(ERROR_MESSAGE_EDIT_RATING_INCOMPLETE);
                } else if (!recipeList.containsRecipe(recipeTitle)) {
                    arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                } else {
                    if (isValidRating(rating)) {
                        if (rating.equals("")) {
                            rating = "unrated";
                        }
                        recipeList.editRating(recipeTitle, rating);
                        recipeStorage.saveFile(recipeList);
                        arrayList.add("The rating of " + "'" + recipeTitle + "'" + " has been edited to: " + rating);
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_RATING);
                    }
                }
            } else {
                arrayList.add(ERROR_MESSAGE_RATING_INCORRECT_FORMAT);
            }

        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    /**
     * Validates the format of input rating from user.
     *
     * @param rating the input rating from user
     * @return true if the input rating is one of "average", "good", and "delicious"
     */
    private boolean isValidRating(String rating) {
        return (rating.equals("") || rating.equals("average") || rating.equals("good") || rating.equals("delicious")
                                  || rating.equals("Average") || rating.equals("Good") || rating.equals("Delicious")
                                  || rating.equals("AVERAGE") || rating.equals("GOOD") || rating.equals("DELICIOUS"));
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
