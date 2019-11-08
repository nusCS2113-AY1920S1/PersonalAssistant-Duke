package DIYeats.logic.suggestion;

import DIYeats.model.meal.Meal;
import DIYeats.model.meal.MealList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MealSuggestionTest {
    private MealList meals;
    private LocalDate suggestionDate;
    private int calorieLimit;
    private int maxMealsToSuggest;
    private String mealSuggestionTypeStr;
    private MealSuggestionAnalytics mealSuggestionAnalytics;

    public MealSuggestionTest() {
        meals = new MealList();
        suggestionDate = LocalDate.now(); // test meal suggestions for today
        calorieLimit = 500; // calorie limit from which suggestions are made
        maxMealsToSuggest = 4; // default value is 5, test if other numbers work
        mealSuggestionTypeStr = "D"; // default value is "L", see if other values work
        mealSuggestionAnalytics = new MealSuggestionAnalytics();
    }

    // helper function to create meal objects
    private Meal createMeal(String nameStr, int calories) {
        HashMap<String, Integer> nutrition = new HashMap<>();
        nutrition.put("calorie", calories);
        return new Meal(nameStr, nutrition);
    }

    // No default  meals stored
    private void setupEmptyDefaultMealList() {
    }

    // Populate default meals with some sample data
    private void setupNormalDefaultMealList() {
        meals.addStoredItem(createMeal("coke", 150));
        meals.addStoredItem(createMeal("biryani", 800));
        meals.addStoredItem(createMeal("prata", 350));
        meals.addStoredItem(createMeal("kopi", 100));
        meals.addStoredItem(createMeal("banana", 100));
        meals.addStoredItem(createMeal("chicken-fried-rice", 450));
        meals.addStoredItem(createMeal("chicken-chop", 550));
    }

    /*
    Tests if analytics runs fine even with empty default meal item list to
    take meal suggestions from. Should return an empty list.
     */
    @Test
    void mealSuggestionEmptyDefaultTest() {
        setupEmptyDefaultMealList();
        ArrayList<Meal> suggestedMeals = mealSuggestionAnalytics.getMealSuggestions(meals, suggestionDate, calorieLimit,
                                                    maxMealsToSuggest, mealSuggestionTypeStr);
        assertTrue(suggestedMeals.size() == 0);
    }

    /*
    Tests if excessive meal suggestions are cut off by the maxMealsToSuggest variable
     */
    @Test
    void mealSuggestionNormalTest() {
        setupNormalDefaultMealList();
        ArrayList<Meal> suggestedMeals = mealSuggestionAnalytics.getMealSuggestions(meals, suggestionDate, calorieLimit,
                maxMealsToSuggest, mealSuggestionTypeStr);
        assertTrue(suggestedMeals.size() == maxMealsToSuggest);
    }

    /*
    Tests if there is off by 1 error for suggested meal calories since the limit
    is the same as a meal item.
     */
    @Test
    void mealSuggestionConstrainedTest() {
        calorieLimit = 150;
        setupNormalDefaultMealList();
        ArrayList<Meal> suggestedMeals = mealSuggestionAnalytics.getMealSuggestions(meals, suggestionDate, calorieLimit,
                maxMealsToSuggest, mealSuggestionTypeStr);
        assertTrue(suggestedMeals.size() == 3);
    }
}
