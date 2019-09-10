package models.tasks;

import java.io.Serializable;

public class Deadline implements ITask, Serializable {
    /**
     * Class representing the Deadline data model.
     */
    private String description;
    private boolean isDone;
    private String initials;
    private String dueDate;

    /**\
     * Constructor of Deadline data model.
     *
     * @param description : Description of new task
     * @param dueDate : Due date of deadline
     */
    public Deadline(String description, String dueDate) {
        this.description = description;
        this.isDone = false;
        this.initials = "D";
        this.dueDate = dueDate;
    }

    @Override
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗");
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String getDescription() {
        return this.description + " (by: " + this.dueDate + ")";
    }

    @Override
    public String getInitials() {
        return this.initials;
    }
}
