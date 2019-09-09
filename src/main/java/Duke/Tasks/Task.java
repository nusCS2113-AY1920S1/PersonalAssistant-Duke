package Duke.Tasks;


public abstract class Task {
    String description;
    private boolean isDone;

    /**
     * Initialises description of task and sets it to !isDone
     * @param description String containing description
     *                    of the task inputted by user
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the status icon based on 'isDone'
     * @return String containing the status icon
     */
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
    }

    /**
     * Method to check whether task is done
     * @return true when task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Method to mark task as done
     */
    public void markAsDone() {
        this.isDone = true;
    }

    public abstract String getDateTime();

    public abstract String getExtra();
}
