package duke.logic.sort;

import duke.model.meal.Meal;

import java.math.BigDecimal;
import java.util.Comparator;

public class SortMealByCost implements Comparator<Meal> {
    @Override
    public int compare(Meal meal, Meal mealCompared) {
        BigDecimal mealCost = new BigDecimal(meal.getCostStr());
        BigDecimal mealComparedCost = new BigDecimal(meal.getCostStr());
        return mealCost.compareTo(mealComparedCost);
    }

    @Override
    public Comparator<Meal> reversed() {
        return null;
    }
}
