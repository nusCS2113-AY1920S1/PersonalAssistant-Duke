package javacake.tasks;


import java.util.Date;

public abstract class Task {
    String description;
    private boolean isDone;
    protected TaskType taskType;

    public enum TaskType {
        TODO, DEADLINE
    }

    /**
     * Initialises description of task and sets it to !isDone.
     * @param description String containing description
     *                    of the task inputted by user
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the status icon based on 'isDone'.
     * @return String containing the status icon
     */
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
    }

    /**
     * Method to check whether task is done.
     * @return true when task is done, false otherwise
     */
    public boolean isDone() {
        return isDone;
    }

    public String getFullString() {
        return null;
    }

    /**
     * Method to mark task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    protected void unmark() {
        this.isDone = false;
    }

    public abstract Date getDateTime();

    public abstract String getExtra();

    public TaskType getTaskType() {
        return taskType;
    }

    public abstract void changeDate(String newDate);
}
