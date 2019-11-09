package diyeats.logic.sort;

import diyeats.model.meal.Meal;

import java.util.Comparator;

//@@author GaryStu
/**
 * SortMealByCalorie is a public class that implements custom comparator for sorting meal by calorie.
 */
public class SortMealByCalorie implements Comparator<Meal> {

    /**
     * Compare meals based on calorie.
     * @param meal to be compared with <code>mealCompared</code>.
     * @param mealCompared to be compared with <code>meal</code>.
     * @return difference <code>meal</code>'s calorie value with <code>mealCompared</code>'s calorie value.
     */
    @Override
    public int compare(Meal meal, Meal mealCompared) {
        return meal.getCalorieValue() - mealCompared.getCalorieValue();
    }

    @Override
    public Comparator<Meal> reversed() {
        return null;
    }
}
