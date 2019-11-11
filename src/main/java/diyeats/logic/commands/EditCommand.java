package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.undo.Undo;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.util.HashMap;

//@@author HashirZahir
public class EditCommand extends Command {
    private int mealIndex;
    private HashMap<String, String> nutritionInfoMap;
    private LocalDate localDate;

    public EditCommand(int index, LocalDate localDate, HashMap<String, String> nutritionInfoMap) {
        this.mealIndex = index;
        this.localDate = localDate;
        this.nutritionInfoMap = nutritionInfoMap;
    }

    public EditCommand(boolean flag, String messageStr) {
        this.isFail = true;
        this.errorStr = messageStr;
    }

    /**
     * Helper function to update existing tags of meal. Also allows changing of meal name and cost
     * @param oldMeal Old meal to be updated
     * @param newNutritionInfoMap Hashmap with updated tags
     * @return New meal object with updated information
     */
    public static Meal getUpdatedMeal(Meal oldMeal, HashMap<String, String> newNutritionInfoMap) {
        String mealNameStr = oldMeal.getDescription();
        String costStr = oldMeal.getCostStr();
        HashMap<String,Integer> nutritionInfoMap = oldMeal.getNutritionalValue();
        if (newNutritionInfoMap.containsKey("name")) {
            mealNameStr = newNutritionInfoMap.get("name");
            newNutritionInfoMap.remove("name");
        }

        if (newNutritionInfoMap.containsKey("cost")) {
            costStr = newNutritionInfoMap.get("cost");
            newNutritionInfoMap.remove("cost");
        }

        for (String keyStr : nutritionInfoMap.keySet()) {
            if (!newNutritionInfoMap.containsKey(keyStr)) {
                newNutritionInfoMap.put(keyStr, nutritionInfoMap.get(keyStr).toString());
            }
        }

        Meal updatedMeal = new Meal(mealNameStr, oldMeal.getDate(), newNutritionInfoMap, costStr);
        updatedMeal.setMealType(oldMeal.getMealType());

        return updatedMeal;
    }

    /**
     * Executes the EditCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param undo the object that facilitates the removal of effect of previous command
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet, Undo undo) {
        if (this.mealIndex >= meals.getMealsList(localDate).size()) {
            ui.showMessage("Edit meal index is out of bounds. Edit not performed");
            return;
        }
        Meal oldMeal = meals.getMealsList(localDate).get(mealIndex);
        undo.undoEdit(mealIndex, oldMeal);
        Meal updatedMeal = getUpdatedMeal(oldMeal, this.nutritionInfoMap);
        meals.updateMealList(localDate, mealIndex, updatedMeal);

        try {
            LocalDate date = updatedMeal.getDate();
            ui.showUpdated(oldMeal, updatedMeal, meals.getMealsList(updatedMeal.getDate()), user, date);
            storage.writeFile(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
    }
}
