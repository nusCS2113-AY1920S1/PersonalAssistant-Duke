package duke.logic.command.inventorycommands;

import duke.logic.command.Command;
import duke.model.list.inventorylist.InventoryList;
import duke.model.list.recipelist.RecipeList;
import duke.model.task.ingredienttasks.Ingredient;
import duke.storage.InventoryStorage;

import java.util.ArrayList;

import static duke.common.InventoryMessages.*;
import static duke.common.Messages.*;
import static duke.common.RecipeMessages.ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST;

public class UseRecipeCommand extends Command<InventoryList, RecipeList, InventoryStorage> {

    public UseRecipeCommand(String userInput) {
        this.userInput = userInput;
    }

    public ArrayList<String> execute(InventoryList inventoryList, RecipeList recipeList, InventoryStorage inventoryStorage) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_USE_RECIPE)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(9) == ' ') {
            String recipeTitle = userInput.split("\\s",2)[1].trim();
            if (!recipeList.containsRecipe(recipeTitle)) {
                arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
            } else {
                // retrieve a list of the required ingredients
                // for every ingredient in the required ingredient list, get the ingredient name
                // check the ingredient name against the inventory, if can minus, minus.
                // if cannot, feedback 'are u sure you have used this recipe? as there is not enough ingred'
                //
                if (inventoryList.getSize() == 0) {
                    arrayList.add(ERROR_MESSAGE_INVENTORY_IS_EMPTY);
                } else {
                    ArrayList<Ingredient> reqIngredients = recipeList.getReqIngredients(recipeTitle);
                    if (inventoryList.removeUsedIngredients(reqIngredients)) {
                        inventoryStorage.saveFile(inventoryList);
                        arrayList.add(MESSAGE_USE_RECIPE_INVENTORY_UPDATED);
                    } else {
                        arrayList.add(ERROR_MESSAGE_HAS_RECIPE_BEEN_USED);
                    }
                }
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
