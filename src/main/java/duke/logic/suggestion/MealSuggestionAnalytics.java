package duke.logic.suggestion;

import duke.model.meal.Meal;
import duke.model.meal.MealList;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class handles all the data analytics aspect of the meal suggestion feature.
 * It calculates the meal preference score based on the meal parameters to be considered
 * and returns the highest meal preference score meal to the user.
 */
public class MealSuggestionAnalytics {

    private HashMap<String, HashMap<String, Integer>> defaultMealSuggestionList;

    public MealSuggestionAnalytics() {

    }

    public ArrayList<Meal> getMealSuggestions(MealList meals, Calendar suggestionDate, int maxMealsToSuggest) {
        defaultMealSuggestionList = meals.getStoredList();
        return new ArrayList<Meal>();
        // return getMealListWithinCalories(calorieLimit);
    }

    private ArrayList<Meal> getMealListWithinCalories(int calorieLimit) {
        ArrayList<Meal> suggestedMealList = new ArrayList<>();
        for (String mealNameStr : defaultMealSuggestionList.keySet()) {
            HashMap<String, Integer> mealDetailsMap = defaultMealSuggestionList.get(mealNameStr);
            if (mealDetailsMap.containsKey("calorie") && mealDetailsMap.get("calorie") < calorieLimit) {
                //suggestedMealList.add(meal);
            }
        }

        sortMealList(suggestedMealList, "calorie");

        return suggestedMealList;
    }

    private void sortMealList(ArrayList<Meal> mealList, String parameterStr) {
        Collections.sort(mealList, Comparator.comparing(meal -> meal.getNutritionalValue().get(parameterStr)));
    }
}
