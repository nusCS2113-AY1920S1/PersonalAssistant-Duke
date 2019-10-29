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

    /**
     * this function overrides the toString() function in meal to represent the full description of a breakfast object.
     * @return <code>"[B]" + super.toString()</code>
     */
    @Override
    public String toString() {
        return "[B]" + super.toString();
    }
}
