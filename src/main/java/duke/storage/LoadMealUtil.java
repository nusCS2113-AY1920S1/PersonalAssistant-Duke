package duke.storage;

import duke.model.Meal;
import duke.model.MealList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class in in charge of loading a meal item into a MealList.
 */
public class LoadMealUtil {

    public static void load(MealList meals, Meal newMeal) {
        HashMap<String, ArrayList<Meal>> mealTracker = meals.getMealTracker();
        if (newMeal.getIsDone()) {
            newMeal.markAsDone();
        }
        String mealDate = newMeal.getDate();
        if (!mealTracker.containsKey(mealDate)) {
            mealTracker.put(mealDate, new ArrayList<Meal>());
            mealTracker.get(mealDate).add(newMeal);
        } else {
            mealTracker.get(mealDate).add(newMeal);
        }
    }
}
