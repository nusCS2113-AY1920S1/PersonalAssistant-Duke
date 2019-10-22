package duke.command.recipecommands;

import duke.command.Command;
import duke.list.recipelist.RecipeIngredientList;

import duke.storage.RecipeIngredientStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.InventoryMessages.*;
import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

public class AddRecipeIngredientCommand extends Command<RecipeIngredientList, Ui, RecipeIngredientStorage> {

    public AddRecipeIngredientCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeIngredientList recipeIngredientList, Ui ui, RecipeIngredientStorage recipeIngredientStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_RECIPE_INGREDIENT)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
        } else if (userInput.trim().charAt(21) == ' ') {
            String description = userInput.split("\\s",2)[1].trim();
            if (description.contains("q/") && description.contains("u/")) {
                String recipeIndex, recipeIngredientName, quantity, unit, additionalInfo, remaining, remaining1, remaining2;
                remaining = description.split("q/", 2)[0].trim();
                if (remaining.contains(" ")) {
                    recipeIndex = remaining.split(" ", 2)[0].trim();
                    if (isParsable(recipeIndex)) {
                        recipeIngredientName = remaining.split(" ", 2)[1].trim();
                        remaining1 = description.split("q/", 2)[1].trim();
                        quantity = remaining1.split("u/", 2)[0].trim();
                        remaining2 = remaining1.split("u/", 2)[1].trim();
                        if (remaining2.contains("a/")) {
                            unit = remaining2.split("a/", 2)[0].trim();
                            additionalInfo = remaining2.split("a/", 2)[1].trim();
                            if (additionalInfo.isEmpty()) {
                                additionalInfo = NO_ADDITIONAL_INFO;
                            }
                        } else {
                            unit = remaining2;
                            additionalInfo = NO_ADDITIONAL_INFO;
                        }
                        if (recipeIndex.isEmpty() || recipeIngredientName.isEmpty() || quantity.isEmpty() || unit.isEmpty()) {
                            arrayList.add(ERROR_MESSAGE_INCOMPLETE);
                        } else {
                            if (isParsable(quantity) && isKnownUnit(unit)) {
                                recipeIngredientList.addRecipeIngredient(recipeIndex, recipeIngredientName, quantity, unit, additionalInfo);
                                recipeIngredientStorage.saveFile(recipeIngredientList);
                                int index = recipeIngredientList.getSize();
                                arrayList.add(MESSAGE_ADDED_TO_INVENTORY);
                            } else if (!isParsable(quantity) && isKnownUnit(unit)) {
                                arrayList.add(ERROR_MESSAGE_INVALID_QUANTITY);
                            } else if (!isKnownUnit(unit) && isParsable(quantity)) {
                                arrayList.add(ERROR_MESSAGE_INVALID_UNIT);
                            } else {
                                arrayList.add(ERROR_MESSAGE_INVALID_QUANTITY_OR_UNIT);
                            }
                        }
                    } else {
                        arrayList.add(ERROR_MESSAGE_INVALID_INDEX);
                    }
                } else {
                    arrayList.add(ERROR_MESSAGE_ADD_INCORRECT_FORMAT);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_ADD_INCORRECT_FORMAT);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    private static boolean isParsable(String quantity) {
        try {
            Integer.parseInt(quantity);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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

//    ArrayList<String> arrayList = new ArrayList<>();
//    String recipeIngredientWeight = "";
//    String recipeIngredientQuantity = "";
//    String recipeIngredientName = "";
//    String recipeIndex = "";
//        if (userInput.trim().equals(COMMAND_ADD_RECIPE_INGREDIENT)) {
//        arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
//        System.out.println("stuck here1");
//    } else if (userInput.trim().charAt(19) == ' ') {
//        String description = userInput.split("\\s", 2)[1].trim();
//        if (description.contains("q/")) {
//            String temp = description.split("q/", 2)[0].trim();
//            recipeIndex = temp.split("\\s", 2)[0].trim();
//            if (isParsableInt(recipeIndex)) {
//                recipeIngredientName = temp.split("\\s", 2)[1].trim();
//                String remaining = description.split("q/", 2)[1].trim();
//                if (remaining.contains("w/")) {
//                    recipeIngredientQuantity = remaining.split("w/")[0].trim();
//                    recipeIngredientWeight = remaining.split("w/")[1].trim();
//                    if (isParsableDouble(recipeIngredientQuantity)) {
//                        recipeIngredientList.addRecipeIngredient(Integer.parseInt(recipeIndex), recipeIngredientName, Double.parseDouble(recipeIngredientQuantity), recipeIngredientWeight);
//                        recipeIngredientStorage.saveFile(recipeIngredientList);
//                        int index = recipeIngredientList.getSize();
//                        System.out.println(index);
//                        arrayList.add(MESSAGE_ADDED + "       " + recipeIngredientList.listRecipeIngredients().get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + " tasks in the list");
//                    } else {
//                        arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_QUANTITY);
//                    }
//                } else {
//                    arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
//                }
//            } else {
//                arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
//            }
//        } else {
//            arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
//        }
//        System.out.println(recipeIngredientName + "......" + recipeIngredientQuantity + "....." + recipeIngredientWeight);
//    } else {
//        arrayList.add(ERROR_MESSAGE_RANDOM);
//    }
//        return arrayList;
//}
//
//    private static boolean isParsableInt(String input) {
//        try {
//            Integer.parseInt(input);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    private static boolean isParsableDouble(String input) {
//        try {
//            Double.parseDouble(input);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }

}
