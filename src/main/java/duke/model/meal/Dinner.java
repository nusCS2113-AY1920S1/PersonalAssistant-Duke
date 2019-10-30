package duke.model.meal;

/**
 * dinner is a public class that inherits from abstract class meal.
 */
public class Dinner extends Meal {

    /**
     * This is the constructor of dinner object.
     * @param description the description of the dinner object
     */
    public Dinner(String description, String details) {
        super(description, details);
        super.type = "D";
        super.cost = "0";
    }
}
