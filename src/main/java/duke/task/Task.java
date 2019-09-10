package duke.task;
/**
 * Represents a <code>Task</code> object.
 * Abstract parent class of all subclasses of <code>Task</code>.
 */
public class Task {
    protected String line;
    protected boolean status;

    /**
     * Constructor for <code>Task</code>.
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
     * Retrieves the description of the <code>Task</code>.
     * @return Description of the <code>Task</code>.
     */
    public String getLine() {
        return line;
    }

    /**
     * Retrieves the status icon of the <code>Task</code>.
     * @return Status icon of the <code>Task</code>.
     */
    public String getStatusIcon() {
        return (status ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Customises the <code>toString()</code> method to print
     * the <code>Task</code> object.
     * @return Customised String for <code>Task</code> object.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + line;
    }
}
