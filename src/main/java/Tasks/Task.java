package Tasks;
/**
 * Represents a task in the Duke program.
 */
public class Task {
    private final String description;
    private boolean isDone;
    private final String type;

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
    private String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return "[" + getStatusIcon()
                + "] " + getDescription();
    }

    public String getDateTime(){
        return "void";
    }

    public abstract String getModCode();
}