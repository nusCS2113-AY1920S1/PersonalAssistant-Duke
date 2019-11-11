package diyeats.logic.command;

import diyeats.logic.commands.EditCommand;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@@author HashirZahir
public class EditMealTest {
    private Meal meal;
    private Meal correctUpdatedMeal;
    private Meal updatedMeal;
    private static LocalDate currDate = LocalDate.now();
    private HashMap<String,String> details;
    private HashMap<String,String> nutritionMap;
    private String costStr;
    private static EditCommand editCommand;

    @BeforeEach
    public void setupMeals() {
        details = new HashMap<>();
        nutritionMap = new HashMap<>();
        nutritionMap.put("calorie", "900");
        nutritionMap.put("fats", "300");
        costStr = "8.50";
        meal = new Meal("biryani", currDate,  nutritionMap, costStr);
        meal.setMealType(MealType.LUNCH);
    }


    @Test
    public void editMealEmptyTest() {
        correctUpdatedMeal = meal;
        updatedMeal = editCommand.getUpdatedMeal(meal, details);
        assertEquals(correctUpdatedMeal.getDescription(), updatedMeal.getDescription());
        assertEquals(correctUpdatedMeal.getNutritionalValue(), updatedMeal.getNutritionalValue());
    }

    @Test
    public void editMealRenameTest() {
        String newNameStr = "chicken-biryani";
        details.put("name", newNameStr);
        correctUpdatedMeal = new Meal(newNameStr, currDate, nutritionMap, costStr);
        updatedMeal = editCommand.getUpdatedMeal(meal, details);
        assertEquals(correctUpdatedMeal.getDescription(), updatedMeal.getDescription());
        assertEquals(correctUpdatedMeal.getNutritionalValue(), updatedMeal.getNutritionalValue());
    }

    @Test
    public void editMealCostTest() {
        String newCostStr = "7.45";
        details.put("cost", newCostStr);
        correctUpdatedMeal = new Meal(meal.getDescription(), currDate, nutritionMap, newCostStr);
        updatedMeal = editCommand.getUpdatedMeal(meal, details);
        assertEquals(correctUpdatedMeal.getDescription(), updatedMeal.getDescription());
        assertEquals(correctUpdatedMeal.getNutritionalValue(), updatedMeal.getNutritionalValue());
    }

    @Test
    public void editMealTagTest() {
        details.put("calorie", "1000");
        details.put("calcium", "150");
        details.put("salt", "130");
        nutritionMap.replace("calorie", "1000");
        nutritionMap.put("calcium", "150");
        nutritionMap.put("salt", "130");

        correctUpdatedMeal = new Meal(meal.getDescription(), currDate, nutritionMap, costStr);
        updatedMeal = editCommand.getUpdatedMeal(meal, details);
        assertEquals(correctUpdatedMeal.getDescription(), updatedMeal.getDescription());
        assertEquals(correctUpdatedMeal.getNutritionalValue(), updatedMeal.getNutritionalValue());
    }
}
