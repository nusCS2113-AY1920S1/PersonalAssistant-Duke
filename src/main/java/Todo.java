/**
 * Class that inherits many of its methods from its superclass Task.
 * Unlike the deadline and event task classes, this class does not parse date inputs.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class Todo extends Task {


    protected String by;

    /**
     * Constructor that takes in the task description.
     *
     * @param description The description of this toodo class.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Method to return the type of task.
     *
     * @return Since this is a todoo class, return [T}
     */
    public String getType() {
        return "[T]";
    }

    /**
     * Method that overrides the same method in the parent class.
     * It returns the full data of this toddo task in a format readable to the user.
     *
     * @return The full data of the task class as a string.
     */
    //Override by using the same name of function from parent
    public String getStatusIcon() {
        return "[T]" + "[" + (isDone ? "Y" : "N") + "] " + this.description;
    }
}

