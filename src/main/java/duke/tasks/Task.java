package duke.tasks;

/**
 * duke.tasks.Task class used in duke.Duke. Extended by duke.tasks.Event, duke.tasks.ToDo, and duke.tasks.Deadline.
 */
public class Task {

    private String description;
    private boolean isDone;

    /**
     * Constructor for the duke.tasks.Task object, which is not used due to the further categorization
     * of duke.tasks.Task objects into the inherited duke.tasks.ToDo, duke.tasks.Event and duke.tasks.Deadline objects
     * that extend the duke.tasks.ToDo Object.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description.trim();
        this.isDone = false;
    }

    /**
     * Returns the icon of the task that represents whether the task is done or not.
     * v represents the task being done.
     * x represents the task being not done.
     *
     * @return the status icon of the task
     */
    public String getStatusIcon() {
        return (isDone ? "v" : "x"); // returns ticks (v) and crosses (x)
    }

    /**
     * Returns the description of the Task object.
     *
     * @return the description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return a String representation of the duke.tasks.Task, as displayed
     * on the command line / in todo_list.txt
     *
     * @return a String representation of the duke.tasks.Task object
     */
    public String toString() {
        return "["
                + this.getStatusIcon()
                + "] "
                + this.description;
    }

    /**
     * Sets the task as done. Note that conversion back to an un-done state is perceived
     * to be unnecessary as it does not make sense for done tasks to be un-done.
     */
    public void setDone() {
        this.isDone = true;
    }

    public String getType() {
        return "T";
    }
}
