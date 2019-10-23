package duke.model;


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
    }

    /**
     * This is the secondary constructor of lunch object for storage parsing.
     * @param description the description of the lunch object
     */
    public Lunch(String description, String[] details) {
        super(description, details);
        super.type = "L";
    }

    /**
     * this function overrides the toString() function in meal to represent the full description of a lunch object.
     * @return <code>"[L]" + super.toString()</code>
     */
    @Override
    public String toString() {
        return "[L]" + super.toString();
    }
}