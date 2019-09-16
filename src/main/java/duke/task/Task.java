package duke.task;

/**
 * Represents a task that stores description and boolean that indicates the task as completed.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the specified description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of a task.
     *
     * @return String of the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a boolean of the task being done or not.
     *
     * @return Boolean of isDone.
     */
    public boolean getisDone() {
        return isDone;
    }

    /**
     * Returns the status icon of a task.
     *
     * @return String of the status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "[✓]" : "[✗]"); //return [✓] : [✗] symbols
    }

    /**
     * Returns the status icon of a task (GUI).
     *
     * @return String of the status icon.
     */
    public String getStatusIconGui() {
        return (isDone ? "[\u2713]" : "[\u2718]"); //return [✓]" : "[✗] symbols
    }

    /**
     * Sets the status icon of a task to true/false.
     *
     * @param setDone The boolean of the task.
     */
    public void setStatusIcon(boolean setDone) {
        isDone = setDone;
    }

    /**
     * Extracting a task content into readable string.
     *
     * @return String that contains the status and the description of the task.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    /**
     * Extracting a task content into readable string (GUI).
     *
     * @return String that contains the status and the description of the task.
     */
    public String toStringGui() {
        return getStatusIconGui() + " " + description;
    }

    /**
     * Extracting a task content into string that is suitable for text file.
     *
     * @return String that contains the status and the description of the task.
     */
    public String toFile() {
        String numStr = "";
        if (isDone) {
            numStr = "1|";
        } else {
            numStr = "0|";
        }
        return  numStr + description;
    }

    /**
     * Retrieves the date of the task as a String format.
     *
     * @return String of Date (Only in Deadline and Event)
     */
    public String getDateString() {
        return null;
    }

}