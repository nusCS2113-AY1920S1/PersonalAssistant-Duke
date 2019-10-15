package Tasks;
/**
 * Represents a task in the Duke program.
 */
public class Task {
    private final String description;
    public boolean isDone;
    private String modCode;

    /**
     * Creates Task object.
     * @param description The description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.modCode ="";
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
        String[] split = description.split(" ");
        String taskDescription = "";
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals(getModCode())) {
                taskDescription += split[i] + " ";
            }
        }
        return taskDescription;
    }

    public String toString() {
        return "[" + getStatusIcon()
                + "] " + getDescription();
    }

    public String getDateTime(){
        return "void";
    }

    public String getModCode() {
        String[] split = description.split(" ");
        return split[0];
    }

    public String toShow() {
        return modCode + "\n" + description;
    }
}