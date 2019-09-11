/**
 * Represents a task that can be created, deleted and completed within the Duke program.
 * Is to be the parent class for all types of tasks available for Duke program.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates task using description.
     * @param description description of task
     */
    public Task(String description){
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates task including boolean representing whether or not the task is completed.
     * @param description task description
     * @param isDone boolean representing state of task completion
     */
    public Task(String description, boolean isDone){
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns a tick if the task is completed, else returns a cross
     * @return Symbol tick or cross
     */
    public String getStatusIcon() {
        return (isDone)? "\u2713" : "\u2718";
    }

    /**
     * Marks a task as completed
     */
    public void markAsDone(){
        this.isDone = true;
    }

    /**
     * Gets description of the task.
     * @return task description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * returns formatted string of task, including status icon and description.
     * @return string containing task in the format [(tick/cross)] (task description)
     */
    @Override
    public String toString(){
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
