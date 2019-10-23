package duke.command.recipecommands;

import duke.command.Command;
import duke.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.task.recipetasks.Recipe;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.*;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

public class EditRecipeCommand extends Command<RecipeList, Ui, RecipeStorage> {

    public EditRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_EDIT_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here edit recipe inner");
        } else if (userInput.trim().charAt(10) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (description.contains("i/")) {
                String recipeIndex, recipeIngredientName, quantity, unit, additionalInfo, remaining, remaining1, remaining2;
                String[] split = description.split("i/", 2);
                if (split.length == 2) {
                    recipeIndex = split[0].trim();
                    remaining = split[1].trim();
                    if (remaining.contains("q/")) {
                        String[] split1 = remaining.split("\\s", 2);
                        if (split1.length == 2) {
                            quantity = split1[1].trim();
//                            recipeList.editRecipe(recipeIndex, quantity);
                            arrayList.add("recipe edited");
                        }
                    }
                }
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    private ArrayList<String> listOfRecipes(HashMap<Integer, Recipe> map, String index, String quantity) {
        Set entries = map.entrySet();
        Iterator entryIter = entries.iterator();
        ArrayList<String> arrayList = new ArrayList<>();
        while (entryIter.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIter.next();
            Object key = entry.getKey();  // Get the key from the entry.
            Recipe value = (Recipe) entry.getValue();  // Get the value.
            arrayList.add("     " + key + " " + value.getRecipeTitle());
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
