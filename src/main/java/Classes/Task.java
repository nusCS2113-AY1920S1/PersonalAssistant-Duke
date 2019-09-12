/**
 * Main class used to represent various tasks
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor that takes in the task or event to be completed.
     * @param description String consisting of the task or event
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the icon depending on the done status of the task
     * @return String representing the completion status
     */
    public String getStatusIcon() {
        return (isDone ? "DONE" : "NOT DONE"); //return tick or X symbols
    }

    /**
     * Returns the task to be completed.
     * @return String representing the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks the task as completed
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Returns the completion status of the task
     * @return True if the task is mark as done, false otherwise
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Returns a printable string of the task holding, all necessary values in the string
     * Sub classes will override this method to print any extra info that it contains
     * @return String that holds the full string to be printed to user
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "]" + " " + this.description;
    }
}