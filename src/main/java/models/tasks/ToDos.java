package models.tasks;

import java.io.Serializable;

public class ToDos implements ITask, Serializable {
    /**
     * Class representing the ToDos data model.
     */
    private String description;
    private boolean isDone;
    private String initials;

    /**
     * Constructor of ToDos data model.
     *
     * @param description : Description of new task
     */
    public ToDos(String description) {
        this.description = description;
        this.isDone = false;
        this.initials = "T";
    }

    @Override
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getInitials() {
        return this.initials;
    }
}
