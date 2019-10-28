package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.model.list.recipelist.RecipeList;
import duke.model.task.recipetasks.Recipe;
import duke.model.task.recipetasks.RecipeTitle;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.*;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

public class ViewRecipeCommand extends Command<RecipeList, Ui, RecipeStorage> {

    public ViewRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_VIEW_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(10) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            System.out.println("this is the description: " + description + "....");
            if (!recipeList.containsRecipe(description)) {
                arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
            } else {
                arrayList.add(MESSAGE_RECIPE_TO_BE_VIEWED);
//                arrayList.addAll(listOfRecipes(recipeList.getRecipeList(), description));
                arrayList.add(recipeList.viewRecipe(description));
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    private ArrayList<String> listOfRecipes(LinkedHashMap<String, Recipe> map, String description) {
        Set entries = map.entrySet();
        Iterator entryIter = entries.iterator();
        ArrayList<String> arrayList = new ArrayList<>();
        int i = 0;
        while (entryIter.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIter.next();
            Object key = entry.getKey();  // Get the key from the entry.
            System.out.println(key);
            Recipe value = (Recipe) entry.getValue();  // Get the value.
            if (key.toString().equals(description)) {
                arrayList.add(MESSAGE_RECIPE_TO_BE_VIEWED);
                arrayList.add(value.getViewString());
            } else {
                arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
            }
            i++;
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
