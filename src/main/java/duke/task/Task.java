package duke.task;

/**
 * Represents a task.  Task is an abstract class that can not be
 * instantiated
 */
public class Task {
    private int id = 0;
    private String description;

    /**
     * .
     *
     * @param id          .
     * @param description .
     */
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     * .
     *
     * @param description .
     */
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
     * @return A string that represents the specific activity associated with the task.
     */
    public int getID() {
        return id;
    }

    /**
     * .
     *
     * @return .
     */
    public String getDescription() {
        return description;
    }

    /**
     * .
     *
     * @param id .
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * .
     *
     * @param description .
     */
    public void setDescription(String description) {
        this.description = description;
    }
}