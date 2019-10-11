package duke.task;


/**
 * Represents a task.  Task is an abstract class that can not be
 * instantiated
 */
public class Task {

    private String description;

    public Task(String description) {
        this.description = description;
    }
    /**
     * Returns a string with the status icon and the description of the task.
     *
     * @return A string in a specific format with the status and description of the task.
     */
    public String printDescription() {
        return " " + description + " ";
    }

    /**
     * Returns the description of the task.
     *
     * @return A string that represents the specific activity associated with
     *         the task.
     */

    public String getDescription() {
        return description;
    }


}