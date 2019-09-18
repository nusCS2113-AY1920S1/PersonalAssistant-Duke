package duke.tasks;

import duke.DateTime;

import java.util.Date;

public abstract class Task {
    private String taskName;
    Boolean isDone;
    DateTime startDate;

    /**
     * Constructor to initialize default values of any instances of children of Task.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
        this.startDate = null;
    }

    private String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getTaskName();
    }

    public String storeString() {
        return Integer.toString((isDone ? 1 : 0)) + " | " + this.getTaskName();
    }

    public String getTaskName() {
        return this.taskName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void markDone() {
        this.isDone = true;
    }

    public Boolean getDone() {
        return isDone;
    }
}
