package diyeats.model.meal;

import java.time.LocalDate;
import java.util.HashMap;

//@@author HashirZahir
/**
 * SuggestMeal class is inherited class of Meal class that has additional parameters to
 * handle the data storage for meal suggestion.
 */
public class SuggestMeal extends Meal implements Comparable<SuggestMeal> {

    private String mealPreferenceParameter = "calorie";

    public SuggestMeal() {
        super();
    }

    public SuggestMeal(String description, HashMap<String, Integer> nutritionValue,
                       LocalDate suggestionDate, String mealTypeStr) {
        super(description, nutritionValue);
        // TODO: Use date objects
        this.date = suggestionDate;
        this.type = mealTypeStr;
    }

    /**
     * Implement default ordering of meal suggestion based on preference score.
     * Currently sorting in ascending order of preference score
     * @param meal Meal to compare against.
     * @return -1,0 or 1 depending on if current meal is less than, equal to or greater than meal.
     */
    @Override
    public int compareTo(SuggestMeal meal) {
        return getMealPreferenceScore().compareTo(
                meal.getMealPreferenceScore());
    }

    // Current meal preference score is just the calories of the meal
    private Integer getMealPreferenceScore() {
        if (getNutritionalValue().containsKey(mealPreferenceParameter)) {
            return getNutritionalValue().get(mealPreferenceParameter);
        } else {
            // TODO: Check if all meals have calorie parameter.
            return 0;
        }
    }
}
