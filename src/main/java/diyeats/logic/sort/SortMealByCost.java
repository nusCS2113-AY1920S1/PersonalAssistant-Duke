package diyeats.logic.sort;

import diyeats.model.meal.Meal;

import java.math.BigDecimal;
import java.util.Comparator;

//@@author GaryStu

/**
 * SortMealByCost is a public class that implements custom comparator for sorting meal by cost.
 */
public class SortMealByCost implements Comparator<Meal> {
    /**
     * compare meals based on their cost.
     * @param meal to be compared with <code>mealCompared</code>
     * @param mealCompared to be compared with <code>meal</code>
     * @return 0 if value of <code>mealCost</code>is equal to that of <code>mealComparedCost</code>.
     *         1 if value of <code>mealCost</code> is greater than that of <code>mealComparedCost</code>.
     *         -1 if value of <code>mealCost</code> is less than that of <code>mealComparedCost</code>.
     */
    @Override
    public int compare(Meal meal, Meal mealCompared) {
        BigDecimal mealCost = new BigDecimal(meal.getCostStr());
        BigDecimal mealComparedCost = new BigDecimal(mealCompared.getCostStr());
        return mealCost.compareTo(mealComparedCost);
    }

    @Override
    public Comparator<Meal> reversed() {
        return null;
    }
}
