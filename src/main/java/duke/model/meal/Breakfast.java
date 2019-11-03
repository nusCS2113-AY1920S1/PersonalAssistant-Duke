package duke.model.meal;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * breakfast is a public class that extends from meal.
 */
public class Breakfast extends Meal {

    /**
     * This is the constructor of breakfast object.
     * @param description the description of the breakfast object
     */
    public Breakfast(String description, LocalDate date, HashMap<String, String> details) {
        super(description, date, details);
        super.type = "B";
        super.cost = "0";
    }
}
