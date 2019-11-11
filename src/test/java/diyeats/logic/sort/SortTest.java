package diyeats.logic.sort;

import diyeats.logic.commands.ListCommand;
import diyeats.model.meal.Breakfast;
import diyeats.model.meal.Dinner;
import diyeats.model.meal.Lunch;
import diyeats.model.meal.Meal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author GaryStu
/**
 * Test regarding the functionality of sort.
 */
public class SortTest {
    private Breakfast breakfastMeal;
    private Lunch lunchMeal;
    private Dinner dinnerMeal;
    private static LocalDate currDate = LocalDate.now();
    private HashMap<String,String> details;
    private HashMap<String,String> nutritionMap;
    private String costStr;
    private ArrayList<Meal> meals = new ArrayList<>();
    private static ListCommand listCommand;

    /**
     * Set up the meals to be tested.
     */
    @BeforeEach
    public void setupMeals() {
        details = new HashMap<>();
        nutritionMap = new HashMap<>();
        nutritionMap.put("calorie", "1000");
        costStr = "50.00";
        breakfastMeal = new Breakfast("porkbelly", currDate, nutritionMap, costStr);
        details = new HashMap<>();
        nutritionMap = new HashMap<>();
        nutritionMap.put("calorie", "1500");
        costStr = "25.00";
        lunchMeal = new Lunch("Gelato Ice Cream", currDate, nutritionMap, costStr);
        details = new HashMap<>();
        nutritionMap = new HashMap<>();
        nutritionMap.put("calorie", "100");
        costStr = "10.00";
        dinnerMeal = new Dinner("cake", currDate, nutritionMap, costStr);
        meals.add(breakfastMeal);
        meals.add(dinnerMeal);
        meals.add(lunchMeal);
    }

    /**
     * test for default sort criterion.
     */
    @Test
    public void defaultSortTest() {
        meals.sort(new SortMealByDefault());
        assertEquals(meals.get(0).getDescription(), "porkbelly");
        assertEquals(meals.get(1).getDescription(), "Gelato Ice Cream");
        assertEquals(meals.get(2).getDescription(), "cake");
    }

    /**
     * test for cost ascending sort criterion .
     */
    @Test
    public void costSortAscendingTest() {
        meals.sort(new SortMealByCost());
        assertEquals(meals.get(0).getDescription(), "cake");
        assertEquals(meals.get(1).getDescription(), "Gelato Ice Cream");
        assertEquals(meals.get(2).getDescription(), "porkbelly");
    }

    /**
     * test for cost descending sort criterion.
     */
    @Test
    public void sortCostDescendingTest() {
        meals.sort(new SortMealByCost().reversed());
        assertEquals(meals.get(2).getDescription(), "cake");
        assertEquals(meals.get(1).getDescription(), "Gelato Ice Cream");
        assertEquals(meals.get(0).getDescription(), "porkbelly");
    }

    /**
     * test for calorie ascending sort criterion.
     */
    @Test
    public void calorieSortAscendingTest() {
        meals.sort(new SortMealByCalorie());
        assertEquals(meals.get(0).getDescription(), "cake");
        assertEquals(meals.get(1).getDescription(), "porkbelly");
        assertEquals(meals.get(2).getDescription(), "Gelato Ice Cream");
    }

    /**
     * test for calorie descending sort criterion.
     */
    @Test
    public void calorieSortDescendingTest() {
        meals.sort(new SortMealByCalorie().reversed());
        assertEquals(meals.get(2).getDescription(), "cake");
        assertEquals(meals.get(1).getDescription(), "porkbelly");
        assertEquals(meals.get(0).getDescription(), "Gelato Ice Cream");
    }
}
