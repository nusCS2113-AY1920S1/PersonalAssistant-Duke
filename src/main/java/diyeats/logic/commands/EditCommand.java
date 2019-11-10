package diyeats.logic.commands;

import diyeats.commons.exceptions.ProgramException;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
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
    public static Meal updateMeal(Meal oldMeal, HashMap<String, String> newNutritionInfoMap) {
        String mealNameStr = oldMeal.getDescription();
        String costStr = oldMeal.getCostStr();
        HashMap<String,Integer> nutritionInfoMap = oldMeal.getNutritionalValue();
        for (String detailsStr : newNutritionInfoMap.keySet()) {
            if (detailsStr.equals("name")) {
                mealNameStr = newNutritionInfoMap.get(detailsStr);
                newNutritionInfoMap.remove(detailsStr);
            } else if (detailsStr.equals("cost")) {
                costStr = newNutritionInfoMap.get(detailsStr);
                newNutritionInfoMap.remove(detailsStr);
            }
        }

        for (String keyStr : nutritionInfoMap.keySet()) {
            newNutritionInfoMap.put(keyStr, nutritionInfoMap.get(keyStr).toString());
        }

        return new Meal(mealNameStr, oldMeal.getDate(), newNutritionInfoMap, costStr);
    }

    /**
     * Executes the EditCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     */
    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        Meal oldMeal = meals.getMealsList(localDate).get(mealIndex);
        Meal updatedMeal = updateMeal(oldMeal, this.nutritionInfoMap);

        ui.showLine();
        try {
            updatedMeal = meals.updateMeal(updatedMeal);
            LocalDate date = updatedMeal.getDate();
            ui.showUpdated(updatedMeal, meals.getMealsList(updatedMeal.getDate()), user, date);
            storage.writeFile(meals);
        } catch (ProgramException e) {
            ui.showMessage(e.getMessage());
        }
        ui.showLine();
    }
}
