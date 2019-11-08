package diyeats.model.meal;


import java.time.LocalDate;
import java.util.HashMap;

/**
 * lunch is a public class that inherits form abstract class meal.
 */
public class Lunch extends Meal {

    /**
     * This is the constructor of lunch object.
     * @param description the description of the lunch object
     */
    public Lunch(String description, LocalDate date, HashMap<String, String> details, String costStr) {
        super(description, date, details, costStr);
        super.type = "L";
        super.mealType = MealType.LUNCH;
    }
}