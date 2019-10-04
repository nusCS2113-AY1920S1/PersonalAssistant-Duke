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

    public void setStatus() {
        this.status = true;
    }

    public boolean getStatus() {
        return status;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String newline) {
        this.line = newline;
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
     * Customises the toString() method to print the Task object.
     *
     * @return Customised String for Task object.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + line;
    }
}
