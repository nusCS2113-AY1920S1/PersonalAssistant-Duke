package duke.model.meal;

/**
 * breakfast is a public class that extends from meal.
 */
public class Breakfast extends Meal {

    /**
     * This is the constructor of breakfast object.
     * @param description the description of the breakfast object
     */
    public Breakfast(String description, String details) {
        super(description, details);
        super.type = "B";
        super.cost = "0";
    }

    /**
     * This is the secondary constructor of breakfast object for storage parsing.
     * @param description the description of the breakfast object
     */
    public Breakfast(String description, String[] details) {
        super(description, details);
        super.type = "B";
        super.cost = "0";
    }
}
