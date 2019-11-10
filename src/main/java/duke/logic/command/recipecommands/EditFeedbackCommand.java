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
 * Handles the edit feedback command.
 */
public class EditFeedbackCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class EditRecipeCommand.
     *
     * @param userInput input command from user
     */
    public EditFeedbackCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the edit feedback command to edit the feedback of a specific recipe.
     *
     * @param recipeList    contains the recipe list
     * @param ui             deals with interactions with the user
     * @param recipeStorage deals with loading tasks from the file and saving recipes in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_EDIT_FEEDBACK)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(12) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (description.contains("f/")) {
                String recipeTitle, feedback;
                String[] split = description.split("f/", 2);
                recipeTitle = split[0].trim();
                feedback = split[1].trim();
                if (recipeTitle.isEmpty()) {
                    arrayList.add(ERROR_MESSAGE_EDIT_FEEDBACK_INCOMPLETE);
                } else if (!recipeList.containsRecipe(recipeTitle)) {
                    arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                } else {
                    if (feedback.equals("")) {
                        feedback = "No feedback yet.";
                    }
                    recipeList.editFeedback(recipeTitle, feedback);
                    recipeStorage.saveFile(recipeList);
                    arrayList.add("The feedback of " + "'" + recipeTitle + "'" + " has been edited to: " + feedback);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_FEEDBACK_INCORRECT_FORMAT);
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
