package duke.model;

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
    }

    /**
     * This is the secondary constructor of dinner object for storage parsing.
     * @param description the description of the breakfast object
     */
    public Dinner(String description, String[] details) {
        super(description, details);
        super.type = "D";
    }

    /**
     * this function overrides the toString() function in meal to represent the full description of a dinner object.
     * @return <code>"[D]" + super.toString()</code>
     */
    @Override
    public String toString() {
        return "[D]" + super.toString();
    }
}
