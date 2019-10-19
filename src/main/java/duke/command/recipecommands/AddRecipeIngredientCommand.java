package duke.command.recipecommands;

import duke.command.Command;
import duke.exception.DukeException;
import duke.list.recipelist.RecipeIngredientList;
import duke.list.recipelist.RecipeList;
import duke.storage.RecipeIngredientStorage;
import duke.storage.RecipeStorage;
import duke.task.recipetasks.Recipe;
import duke.task.recipetasks.RecipeTitle;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.*;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.*;

public class AddRecipeIngredientCommand extends Command<RecipeList, Ui, RecipeStorage> {

    public AddRecipeIngredientCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws DukeException {
        ArrayList<String> arrayList = new ArrayList<>();
        String recipeIndex = "";

        if (userInput.trim().equals(COMMAND_ADD_RECIPE_INGREDIENT)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here1");
        } else if (userInput.trim().charAt(19) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (description.contains("q/")) {
                String temp = description.split("q/", 2)[0].trim();
                recipeIndex = temp.split("\\s", 2)[0].trim();
                if (isParsableInt(recipeIndex)) {
                    arrayList.addAll(listOfRecipes(recipeList.getRecipeList(), Integer.parseInt(recipeIndex), description));
                }else {
                    arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
                }
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    private ArrayList<String> listOfRecipes(LinkedHashMap<RecipeTitle, Recipe> map, int index, String description) throws DukeException {
        Set entries = map.entrySet();
        Iterator entryIter = entries.iterator();
        ArrayList<String> arrayList = new ArrayList<>();
        while (entryIter.hasNext()) {
            Map.Entry entry = (Map.Entry) entryIter.next();
            RecipeTitle key = (RecipeTitle) entry.getKey();  // Get the key from the entry.
            if (key.getIndex() == index) {
                Recipe value = (Recipe) entry.getValue();  // Get the value
                value.setRequiredIngredients(description);
                arrayList.add("added successfully");
                break;
            } else {
                arrayList.add("error in adding");
            }
        }
        return arrayList;
    }

    private static boolean isParsableInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static boolean isParsableDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}


//    @Override
//    public ArrayList<String> execute(RecipeIngredientList recipeIngredientList, Ui ui, RecipeIngredientStorage recipeIngredientStorage) throws ParseException {
//        ArrayList<String> arrayList = new ArrayList<>();
//        String recipeIngredientWeight = "";
//        String recipeIngredientQuantity = "";
//        String recipeIngredientName = "";
//        String recipeIndex = "";
//        if (userInput.trim().equals(COMMAND_ADD_RECIPE_INGREDIENT)) {
//            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
//            System.out.println("stuck here1");
//        } else if (userInput.trim().charAt(19) == ' ') {
//            String description = userInput.split("\\s", 2)[1].trim();
//            if (description.contains("q/")) {
//                String temp = description.split("q/", 2)[0].trim();
//                recipeIndex = temp.split("\\s", 2)[0].trim();
//                if (isParsableInt(recipeIndex)) {
//                    recipeIngredientName = temp.split("\\s", 2)[1].trim();
//                    String remaining = description.split("q/", 2)[1].trim();
//                    if (remaining.contains("w/")) {
//                        recipeIngredientQuantity = remaining.split("w/")[0].trim();
//                        recipeIngredientWeight = remaining.split("w/")[1].trim();
//                        if (isParsableDouble(recipeIngredientQuantity)) {
//                            recipeIngredientList.addRecipeIngredient(Integer.parseInt(recipeIndex), recipeIngredientName, Double.parseDouble(recipeIngredientQuantity), recipeIngredientWeight);
//                            recipeIngredientStorage.saveFile(recipeIngredientList);
//                            int index = recipeIngredientList.getSize();
//                            System.out.println(index);
//                            arrayList.add(MESSAGE_ADDED + "       " + recipeIngredientList.listRecipeIngredients().get(index - 1) + "\n" + MESSAGE_ITEMS1 + index + " tasks in the list");
//                        } else {
//                            arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_QUANTITY);
//                        }
//                    } else {
//                        arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
//                    }
//                } else {
//                    arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
//                }
//            } else {
//                arrayList.add(ERROR_MESSAGE_INVALID_RECIPE_FORMAT);
//            }
//            System.out.println(recipeIngredientName + "......" + recipeIngredientQuantity + "....." + recipeIngredientWeight);
//        } else {
//            arrayList.add(ERROR_MESSAGE_RANDOM);
//        }
//        return arrayList;
//    }