package oof.model.task;

/**
 * Abstract parent class of all subclasses of Task.
 */
public abstract class Task {

    static final String DELIMITER = "||";
    static final int INDEX_DATE = 0;
    static final int INDEX_TIME = 1;
    protected String description;
    private boolean status;

    /**
     * Constructor for Task.
     *
     * @param description Description of task.
     */
    public Task(String description) {
        this.description = description;
        this.status = false;
    }

    public void setStatus() {
        this.status = true;
    }

    public boolean getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the status icon of the Task.
     *
     * @return Status icon of the Task.
     */
    public String getStatusIcon() {
        return (status ? "Y" : "N"); //return tick or X symbols
    }

    /**
     * Converts a task object to string format for storage.
     * @return Task object in string format for storage.
     */
    public abstract String toStorageString();

    /**
     * Customises the toString() method to print the Task object.
     *
     * @return Customised String for Task object.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
