package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;
import static duke.common.InventoryMessages.*;

public class EditRequiredIngredientCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class EditRequiredIngredientCommand.
     *
     * @param userInput input command from user
     */
    public EditRequiredIngredientCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_EDIT_REQ_INGREDIENT)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(17) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            String recipeTitle, position, ingredientName, quantity, unit, additionalInfo, remaining, remaining2, remaining3, remaining4;
             if (hasOneCommand(description)) {
                 String command = whichCommand(description);

                 if (isIns(command) && hasAllIngredientFields(description)) {
                     String[] split = description.split(command, 2);
                     recipeTitle = split[0].trim();
                     remaining = split[1].trim();
                     position = remaining.split("n/", 2)[0].trim();
                     remaining2 = remaining.split("n/", 2)[1].trim();
                     ingredientName = remaining2.split("q/", 2)[0].trim();
                     remaining3 = remaining2.split("q/", 2)[1].trim();
                     quantity = remaining3.split("u/", 2)[0].trim();
                     remaining4 = remaining3.split("u/", 2)[1].trim();

                     if (remaining4.contains("a/")) {
                         unit = remaining4.split("a/", 2)[0].trim();
                         additionalInfo = remaining4.split("a/", 2)[1].trim();
                         if (additionalInfo.isEmpty()) {
                             additionalInfo = NO_ADDITIONAL_INFO;
                         }
                     } else {
                         unit = remaining4;
                         additionalInfo = NO_ADDITIONAL_INFO;
                     }
                     if (recipeTitle.isEmpty() || position.isEmpty() || ingredientName.isEmpty()) {
                         arrayList.add(ERROR_MESSAGE_EDIT_INGREDIENT_INS_INCOMPLETE);
                     } else if (!recipeList.containsRecipe(recipeTitle)) {
                         arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                     } else {
                         if (recipeList.containsRecipeIngredient(recipeTitle, ingredientName).equals("null")) {
                             if ((isParsable(quantity) || isParsableDbl(quantity)) && isKnownUnit(unit) && isParsable(position) && isValidPosition(recipeTitle, position, recipeList)) {
                                 // what if they anyhow input position?
                                 recipeList.insertReqIngredient(recipeTitle, position, ingredientName, quantity, unit, additionalInfo);
                                 recipeStorage.saveFile(recipeList);
                                 arrayList.add(MESSAGE_ADDED_TO_REQ_INGREDIENTS + "\n" + "       " + ingredientName);
                             } else {
                                 if (!isParsable(quantity) || !isParsableDbl(quantity)) {
                                     arrayList.add(ERROR_MESSAGE_INVALID_QUANTITY + "\n");
                                 }
                                 if (!isKnownUnit(unit)) {
                                     arrayList.add(ERROR_MESSAGE_INVALID_UNIT + "\n");
                                 }
                                 if (!isParsable(position)) {
                                     arrayList.add(ERROR_MESSAGE_INVALID_INDEX + "\n");
                                 }
                                 if (!isValidPosition(recipeTitle, position, recipeList)) {
                                     arrayList.add(ERROR_MESSAGE_REQ_INGREDIENT_INVALID_POSITION);
                                 } else {
                                     arrayList.add(ERROR_MESSAGE_EDIT_INGREDIENT_INS_INCORRECT_FORMAT + "\n");
                                 }
                             }
                         } else {
                             String prevIngredient = recipeList.containsRecipeIngredient(recipeTitle, ingredientName);
                             String[] part = prevIngredient.split(",", 5);
                             String index = part[4].trim();
                             if (quantity.isEmpty()) {
                                 quantity = part[1].trim();
                             }
                             if (unit.isEmpty()) {
                                 unit = part[2].trim();
                             }
                             if (additionalInfo.equals(NO_ADDITIONAL_INFO)){
                                 additionalInfo = part[3].trim();
                             }
                             recipeList.removeDupReqIngredient(Integer.parseInt(index), recipeTitle);
                             recipeList.insertReqIngredient(recipeTitle, position, ingredientName, quantity, unit, additionalInfo);
                             recipeStorage.saveFile(recipeList);
                             arrayList.add(MESSAGE_ADDED_TO_REQ_INGREDIENTS + "\n" + "       " + ingredientName);
                         }
                     }
                 }


                 else if (isDel(command)) {
                     String[] split = description.split(command, 2);
                     recipeTitle = split[0].trim();
                     position = split[1].trim();
                     if (recipeTitle.isEmpty() || position.isEmpty()) {
                         arrayList.add(ERROR_MESSAGE_EDIT_INGREDIENT_DEL_INCOMPLETE);
                     } else if (!recipeList.containsRecipe(recipeTitle)) {
                         arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                     } else {
                         if (isParsable(position)) {
                             String deletedIngredientName = recipeList.deleteReqIngredient(recipeTitle, position);
                             recipeStorage.saveFile(recipeList);
                             arrayList.add(MESSAGE_DELETED_FROM_REQ_INGREDIENTS + "\n" + "       " + deletedIngredientName);
                         } else {
                             arrayList.add(ERROR_MESSAGE_INVALID_INDEX);
                         }
                     }
                 }


                 else if (isApp(command) && hasAllIngredientFields(description)) {
                     String[] split = description.split(command, 2);
                     recipeTitle = split[0].trim();
                     remaining = split[1].trim();
                     // position = remaining.split("n/", 2)[0].trim();
                     remaining2 = remaining.split("n/", 2)[1].trim();
                     ingredientName = remaining2.split("q/", 2)[0].trim();
                     remaining3 = remaining2.split("q/", 2)[1].trim();
                     quantity = remaining3.split("u/", 2)[0].trim();
                     remaining4 = remaining3.split("u/", 2)[1].trim();

                     if (remaining4.contains("a/")) {
                         unit = remaining4.split("a/", 2)[0].trim();
                         additionalInfo = remaining4.split("a/", 2)[1].trim();
                         if (additionalInfo.isEmpty()) {
                             additionalInfo = NO_ADDITIONAL_INFO;
                         }
                     } else {
                         unit = remaining4;
                         additionalInfo = NO_ADDITIONAL_INFO;
                     }

                     if (recipeTitle.isEmpty() || ingredientName.isEmpty()) {
                         arrayList.add(ERROR_MESSAGE_EDIT_INGREDIENT_APP_INCOMPLETE);
                     } else if (!recipeList.containsRecipe(recipeTitle)) {
                         arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                     } else {
                         if (recipeList.containsRecipeIngredient(recipeTitle, ingredientName).equals("null")) {
                             if ((isParsable(quantity) || isParsableDbl(quantity)) && isKnownUnit(unit)) {

                                 // what if they anyhow input position?
                                 recipeList.appendReqIngredient(recipeTitle, ingredientName, quantity, unit, additionalInfo);
                                 recipeStorage.saveFile(recipeList);
                                 arrayList.add(MESSAGE_ADDED_TO_REQ_INGREDIENTS + "\n" + "       " + ingredientName);
                             } else if ((!isParsable(quantity) || !isParsableDbl(quantity)) && isKnownUnit(unit)){
                                 arrayList.add(ERROR_MESSAGE_INVALID_QUANTITY);
                             } else  if (!isKnownUnit(unit) && (isParsable(quantity) || isParsableDbl(quantity))) {
                                 arrayList.add(ERROR_MESSAGE_INVALID_UNIT);
                             } else {
                                 arrayList.add(ERROR_MESSAGE_INVALID_QUANTITY_OR_UNIT);
                             }
                         } else {
                             String prevIngredient = recipeList.containsRecipeIngredient(recipeTitle, ingredientName);
                             String[] part = prevIngredient.split(",", 5);
                             String index = part[4].trim();
                             if (quantity.isEmpty()) {
                                 quantity = part[1].trim();
                             }
                             if (unit.isEmpty()) {
                                 unit = part[2].trim();
                             }
                             if (additionalInfo.equals(NO_ADDITIONAL_INFO)){
                                 additionalInfo = part[3].trim();
                             }
                             recipeList.removeDupReqIngredient(Integer.parseInt(index), recipeTitle);
                             recipeList.appendReqIngredient(recipeTitle, ingredientName, quantity, unit, additionalInfo);
                             recipeStorage.saveFile(recipeList);
                             arrayList.add(MESSAGE_ADDED_TO_REQ_INGREDIENTS + "\n" + "       " + ingredientName);
                         }
                     }
                 }

                 else if (isClr(command)) {
                     String[] split = description.split(command, 2);
                     recipeTitle = split[0].trim();
                     if (recipeTitle.isEmpty()) {
                         arrayList.add(ERROR_MESSAGE_EDIT_INGREDIENT_CLR_INCOMPLETE);
                     } else if (!recipeList.containsRecipe(recipeTitle)) {
                         arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                     } else {
                         recipeList.clearReqIngredient(recipeTitle);
                         recipeStorage.saveFile(recipeList);
                         arrayList.add(MESSAGE_CLEARED_REQ_INGREDIENTS);
                     }
                 }
                 else {
                     arrayList.add(ERROR_MESSAGE_EDIT_REQ_INGREDIENT_INCORRECT_FORMAT);
                 }
             } else {
                 arrayList.add(ERROR_MESSAGE_REQ_INGREDIENT_NO_EDIT_COMMAND);
             }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    /**
     * Validates that the input contains all of the ingredient's name, quantity, unit, and additional information.
     *
     * @param description the input information from user
     * @return true if all of the ingredient's name, quantity, unit, and additional information are provided
     */
    private boolean hasAllIngredientFields(String description) {
        if (description.contains("n/") && description.contains("q/") && description.contains("u/")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validates that the input contains only one command.
     *
     * @param description the input information from user
     * @return true if the input contains only one command
     */
    private static boolean hasOneCommand(String description) {
        int i = 0;
        if (description.contains("ins/")) {
            ++i;
        } else if (description.contains("del/")) {
            ++i;
        } else if (description.contains("app/")) {
            ++i;
        } else if (description.contains("clr/")) {
            ++i;
        }

        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the type of the command from user.
     *
     * @param description the input information from user
     * @return the command type
     */
    private String whichCommand(String description) {
        if (description.contains("ins/")) {
            return "ins/";
        } else if (description.contains("del/")) {
            return "del/";
        } else if (description.contains("app/")) {
            return "app/";
        } else {
            return "clr/";
        }
    }

    /**
     * Checks if the command type is insert.
     *
     * @param command the input information by user
     * @return true if the command type is insert
     */
    private boolean isIns(String command) {
        if (command.equals("ins/")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the command type is delete.
     *
     * @param command the input information from user
     * @return true if the command type is delete
     */
    private boolean isDel(String command) {
        if (command.equals("del/")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the command type is append.
     *
     * @param command the input information from user
     * @return true if the command type is append
     */
    private boolean isApp(String command) {
        if (command.equals("app/")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if the command type is clear.
     *
     * @param command the input information from user
     * @return true if the command type is clear
     */
    private boolean isClr(String command) {
        if (command.equals("clr/")) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isParsable(String quantity) {
        try {
            Integer.parseInt(quantity);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isParsableDbl(String quantity) {
        try {
            Double.parseDouble(quantity);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates the input position.
     *
     * @param recipeTitle name of the recipe
     * @param  position index of the preparation step
     * @param recipeList list of all recipes
     * @return true if the position is valid
     */
    private boolean isValidPosition(String recipeTitle, String position, RecipeList recipeList) {
        return Integer.parseInt(position) <= (recipeList.getRecipeList().get(recipeTitle).getRequiredIngredients().getSize() + 1);
    }

    /**
     * Validates the input unit.
     *
     * @param unit the input unit from user
     * @return true if the unit is known
     */
    private static boolean isKnownUnit(String unit) { // edit this part.
        if (unit.equals("g") || unit.equals("kg") || unit.equals("l") || unit.equals("ml") || unit.equals("cup") || unit.equals("teaspoon") || unit.equals("tablespoon")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
