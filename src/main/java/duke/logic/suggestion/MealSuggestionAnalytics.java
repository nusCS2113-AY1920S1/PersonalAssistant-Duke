package duke.logic.suggestion;

import duke.model.meal.Meal;
import duke.model.meal.MealList;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class handles all the data analytics aspect of the meal suggestion feature.
 * It calculates the meal preference score based on the meal parameters to be considered
 * and returns the highest meal preference score meal to the user.
 */
public class MealSuggestionAnalytics {

    public MealSuggestionAnalytics() {

    }

    public ArrayList<Meal> getMealSuggestions(ArrayList<Meal> defaultMealList, MealList mealList,
                                              Calendar suggestionDate, int maxMealsToSuggest) {
        // TODO: Add logic to return filtered and sorted suggested meals
        return defaultMealList;
    }
}
