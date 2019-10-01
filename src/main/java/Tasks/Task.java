package Tasks;
/**
 * Represents a task in the Duke program.
 */
public class Task {
    private String description;
    private boolean isDone;
    private String type;

    /**
     * Creates Task object.
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.type = "";

    }

    public String getType() {
        return "void";
    }

    /**
     * Checks whether the task is completed.
     * @return This returns a tick or cross depending on the boolean value of isDone
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Sets whether the task is done.
     * @param done The boolean value of whether the task is done
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Retrieves the description of a task.
     * @return This returns the string description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Converts the task to a string with a tick or cross to indicate
     * whether it is done and its description.
     * @return This returns the string format of the task
     */
    public String toString() {
        return "[" + getStatusIcon()
                + "] " + getDescription();
    }

    public String getDateTime(){
        return "void";
    }
}