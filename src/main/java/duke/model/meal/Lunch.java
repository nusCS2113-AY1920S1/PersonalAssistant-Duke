package duke.model.meal;


/**
 * lunch is a public class that inherits form abstract class meal.
 */
public class Lunch extends Meal {

    /**
     * This is the constructor of lunch object.
     * @param description the description of the lunch object
     */
    public Lunch(String description, String details) {
        super(description, details);
        super.type = "L";
        super.cost = "0";
    }
}