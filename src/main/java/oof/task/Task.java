package oof.task;

/**
 * Abstract parent class of all subclasses of Task.
 */
public abstract class Task {

    protected String line;
    protected boolean status;

    /**
     * Constructor for Task.
     *
     * @param line Command inputted by user.
     */
    public Task(String line) {
        this.line = line;
        this.status = false;
    }

    /**
     * Sets the task as done.
     */
    public void setStatus() {
        this.status = true;
    }

    /**
     * Retrieves the description of the Task.
     *
     * @return Description of the Task.
     */
    public String getLine() {
        return line;
    }

    /**
     * Retrieves the status icon of the Task.
     *
     * @return Status icon of the Task.
     */
    public String getStatusIcon() {
        return (status ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Customises the toString() method to print the Task object.
     *
     * @return Customised String for Task object.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + line;
    }
}
