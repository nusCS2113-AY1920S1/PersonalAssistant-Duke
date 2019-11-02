package duke.model.meal;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * dinner is a public class that inherits from abstract class meal.
 */
public class Dinner extends Meal {

    /**
     * This is the constructor of dinner object.
     * @param description the description of the dinner object
     */
    public Dinner(String description, LocalDate date, HashMap<String, String> details) {
        super(description, date, details);
        super.type = "D";
        super.cost = "0";
    }
}
