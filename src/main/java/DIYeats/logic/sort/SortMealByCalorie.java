package DIYeats.logic.sort;

import DIYeats.model.meal.Meal;

import java.util.Comparator;

public class SortMealByCalorie implements Comparator<Meal> {

    @Override
    public int compare(Meal meal, Meal mealCompared) {
        return meal.getCalorieValue() - mealCompared.getCalorieValue();
    }

    @Override
    public Comparator<Meal> reversed() {
        return null;
    }
}
