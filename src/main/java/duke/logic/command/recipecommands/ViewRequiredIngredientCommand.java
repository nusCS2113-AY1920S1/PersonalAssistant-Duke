package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static duke.common.Messages.ERROR_MESSAGE_GENERAL;
import static duke.common.Messages.MESSAGE_FOLLOWUP_NUll;
import static duke.common.RecipeMessages.*;

/**
 * Handles the view required ingredient command.
 */
public class ViewRequiredIngredientCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class ViewRequiredIngredientCommand.
     *
     * @param userInput input command from user
     */
    public ViewRequiredIngredientCommand(String userInput) {
        this.userInput = userInput;
    }

    /**
     * Processes the view required ingredient command to view the required ingredients for a range of recipes.
     *
     * @param recipeList    contains the recipe list
     * @param ui             deals with interactions with the user
     * @param recipeStorage deals with loading tasks from the file and saving recipes in the file
     * @return an array list consist of the results or prompts to be displayed to user
     */
    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        HashMap<String, Double> tempMap = new HashMap<>();
        HashMap<String, Double> mergeMap = new HashMap<>();
        if (userInput.trim().equals(COMMAND_VIEW_REQ_INGREDIENT)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(17) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (description.trim().contains(",")) {
                String[] split = description.split(",");
                for (int i = 0; i < split.length; i++) {
                    if (!recipeList.containsRecipe(split[i].trim())) {
                        arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST + ": " + split[i].trim());
                    } else {
                        if (i == 0) {
                            arrayList.add(MESSAGE_RECIPE_TO_BE_VIEWED);
                            mergeMap.putAll(recipeList.getRecipeList().get(split[i].trim()).getRequiredIngredients().getAllIngredient());
                        } else {
                            tempMap.putAll(recipeList.getRecipeList().get(split[i].trim()).getRequiredIngredients().getAllIngredient());
                            tempMap.forEach(
                                (key, value) -> mergeMap.merge( key, value, (v1, v2) -> v1 += v2)
                            );
                        }
                        String ingredient =  recipeList.viewReqIngredient(split[i].trim());
                        arrayList.add("Recipe Title: " + split[i].trim() + "\n" + ingredient);
                    }
                }
                AtomicInteger i = new AtomicInteger();
                mergeMap.forEach(
                    (key, value) -> temp.add(i.incrementAndGet() + ". " + key + " | " + value)
                );
                arrayList.add("\n" + "Combined list of ingredient with the respective amount: ");
                arrayList.addAll(temp);
            } else {
                if (!recipeList.containsRecipe(description)) {
                    arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                } else {
                    arrayList.add(MESSAGE_RECIPE_TO_BE_VIEWED);
                    arrayList.add(recipeList.viewReqIngredient(description));
                }
            }
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
