package diyeats.logic.sort;

import diyeats.model.meal.Meal;

import java.util.Comparator;

//@@author GaryStu
/**
 * SortMealByDefault is a public class that implement custom comparator for sorting meal by mealType.
 */
public class SortMealByDefault implements Comparator<Meal> {
    /**
     * compare meals based on meal type order (breakfast, lunch, dinner).
     * @param meal to be compared with <code>mealCompared</code>
     * @param mealCompared to be compared with <code>meal</code>
     * @return return -1 if <code>meal.getMealType()</code> is earlier than <code>mealCompared.getMealType()</code>.
     *         return 0 if <code>meal.getMealType()</code> is equal to <code>mealCompared.getMealType()</code>.
     *         return 1 if <code>meal.getMealType()</code> is later than <code>mealCompared.getMealType</code>.
     */
    @Override
    public int compare(Meal meal, Meal mealCompared) {
        return meal.getMealType().compareTo(mealCompared.getMealType());
    }
}
