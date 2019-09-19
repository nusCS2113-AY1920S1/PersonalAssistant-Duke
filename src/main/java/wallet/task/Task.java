package wallet.task;

public class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a new Task object.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Retrieve the status icon of the task based on isDone.
     *
     * @return The corresponding status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "✓" : "✘"); //return tick or X symbols
    }

    /**
     * Get the current status of the task.
     *
     * @return true if the task is done.
     */
    public boolean getStatus() {
        return isDone;
    }

    /**
     * Set the isDone variable to true, showing that the task is done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Retrieve the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Update the description of the task.
     *
     * @param description The new description of the task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Outputs the string with the correct format for printing to UI.
     *
     * @return The string formatted for printing to UI.
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * Outputs the string with the correct format for writing to output file.
     *
     * @return The string formatted for writing to output file.
     */
    public String writeToFile() {
        return (this.isDone ? '1' : '0') + "," + this.getDescription();
    }
}