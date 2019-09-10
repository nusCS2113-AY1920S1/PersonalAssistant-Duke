package duke.tasks;

/**
 * Task is a public class that represents the tasks in duke
 * A task object encapsulates the description of the task, the type of task it is, and whether
 * the task is done
 */
public class Task {
    protected String description;
    protected String type = "";
    protected boolean isDone;

    /**
     * This is the constructor of Task object
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * This function checks whether the particular task object is done and return the string accordingly
     * @return <code>[\u2713]</code> if the task is done
     *          <code>[\u2718]</code> if the task is not done
     */
    public String getStatusIcon() {
        return (isDone ? "[\u2713]" : "[\u2718]"); //return tick or X symbols
    }

    /**
     * This is a getter for description
     * @return description of the task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * This is a setter for isDone
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * This is a getter for isDone
     * @return isDone status of the task
     */
    public boolean getisDone() {
        return this.isDone;
    }

    /**
     * This is a getter for the type
     * @return type of the task
     */
    public String getType() {
        return this.type;
    }

    /**
     * This function overrides the toString() function in the object class
     * @return the status icon and the description of the task
     */
    @Override
    public String toString() {
        return this.getStatusIcon() + " " + this.description;
        //TODO: refactor this by using type also
    }

}