package duke.logic.command.recipecommands;

import duke.logic.command.Command;
import duke.model.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

public class EditPrepStepCommand extends Command<RecipeList, Ui, RecipeStorage> {

    /**
     * Constructor for class EditPrepStepCommand.
     *
     * @param userInput input command from user
     */
    public EditPrepStepCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_EDIT_PREPSTEP)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        }
        else if (userInput.trim().charAt(12) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            String recipeTitle, position, prepStep, remaining;
            if (hasOneCommand(description)) {
                String command = whichCommand(description);

                if (isIns(command)) {
                    if (!description.contains("stetp/")) {
                        arrayList.add(ERROR_MESSAGE_EDIT_PREPSTEP_INS_INCORRECT_FORMAT + "\n");
                    } else {
                        String[] split = description.split(command, 2);
                        recipeTitle = split[0].trim();
                        remaining = split[1].trim();
                        position = remaining.split("step/", 2)[0].trim();
                        prepStep = remaining.split("step/", 2)[1].trim();

                        if (recipeTitle.isEmpty() || position.isEmpty() || prepStep.isEmpty()) {
                            arrayList.add(ERROR_MESSAGE_EDIT_PREPSTEP_INS_INCOMPLETE);
                        } else if (!recipeList.containsRecipe(recipeTitle)) {
                            arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                        } else {
                            if (isParsable(position) && isValidPosition(recipeTitle, position, recipeList)) {
                                recipeList.insertPrepStep(recipeTitle, position, prepStep);
                                recipeStorage.saveFile(recipeList);
                                arrayList.add(MESSAGE_ADDED_TO_PREPSTEPS + "\n" + "       " + prepStep);
                            } else {
                                if (!isParsable(position)) {
                                    arrayList.add(ERROR_MESSAGE_INVALID_INDEX + "\n");
                                }
                                if (!isValidPosition(recipeTitle, position, recipeList)) {
                                    arrayList.add(ERROR_MESSAGE_PREPSTEP_INVALID_POSITION);
                                } else {
                                    arrayList.add(ERROR_MESSAGE_EDIT_PREPSTEP_INS_INCORRECT_FORMAT + "\n");
                                }
                            }
                        }
                    }
                } else if (isDel(command)) {
                    String[] split = description.split(command, 2);
                    recipeTitle = split[0].trim();
                    position = split[1].trim();
                    if (recipeTitle.isEmpty() || position.isEmpty()) {
                        arrayList.add(ERROR_MESSAGE_EDIT_PREPSTEP_DEL_INCOMPLETE);
                    } else if (!recipeList.containsRecipe(recipeTitle)) {
                        arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                    } else {
                        if (isParsable(position)) {
                            String deletedPrepStep = recipeList.deletePrepStep(recipeTitle, position);
                            recipeStorage.saveFile(recipeList);
                            arrayList.add(MESSAGE_DELETED_FROM_PREPSTEPS + "\n" + "       " + deletedPrepStep);
                        } else {
                            arrayList.add(ERROR_MESSAGE_INVALID_INDEX);
                        }
                    }
                }

                else if (isApp(command)) {
                    if (!description.contains("step/")) {
                        arrayList.add(ERROR_MESSAGE_EDIT_PREPSTEP_INS_INCORRECT_FORMAT + "\n");
                    } else {
                        String[] split = description.split(command, 2);
                        recipeTitle = split[0].trim();
                        remaining = split[1].trim();
                        prepStep = remaining.split("step/", 2)[1].trim();

                        if (recipeTitle.isEmpty() || prepStep.isEmpty()) {
                            arrayList.add(ERROR_MESSAGE_EDIT_PREPSTEP_APP_INCOMPLETE);
                        } else if (!recipeList.containsRecipe(recipeTitle)) {
                            arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                        } else {
                            recipeList.appendPrepStep(recipeTitle, prepStep);
                            recipeStorage.saveFile(recipeList);
                            arrayList.add(MESSAGE_ADDED_TO_PREPSTEPS + "\n" + "       " + prepStep);
                        }
                    }
                }


                else if (isClr(command)) {
                    String[] split = description.split(command, 2);
                    recipeTitle = split[0].trim();
                    if (recipeTitle.isEmpty()) {
                        arrayList.add(ERROR_MESSAGE_EDIT_PREPSTEP_CLR_INCOMPLETE);
                    } else if (!recipeList.containsRecipe(recipeTitle)) {
                        arrayList.add(ERROR_MESSAGE_RECIPE_DOES_NOT_EXIST);
                    } else {
                        recipeList.clearPrepStep(recipeTitle);
                        recipeStorage.saveFile(recipeList);
                        arrayList.add(MESSAGE_CLEARED_PREPSTEPS);
                    }
                }
                else {
                    arrayList.add(ERROR_MESSAGE_EDIT_PREPSTEP_INCORRECT_FORMAT);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_PREPSTEP_NO_EDIT_COMMAND);
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
