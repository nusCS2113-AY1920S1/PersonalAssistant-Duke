package duke.items;

/**
 * Task is an abstract class that stores the description and done status of a task.
 * Is extended by Todo, Deadline and Event classes.
 */

public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;
    protected Task doAfter; //Can extend to a list of tasks that have to be done before.

    protected enum TaskType {
        TODO, DEADLINE, EVENT
    }

    public Task() {
        this.description = "None";
        this.isDone = false;
    }

    /**
     * All tasks contain a description and isDone status, and also belong to a type.
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        return (isDone ? "[\u2713]" : "[\u2718]"); //return tick or X symbols
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setDoAfter(Task doAfter) {
        this.doAfter = doAfter;
    }

    public void printTaskDetails() {
        System.out.println(toString());
    }

    /**
     * Method that returns a string with the details for saving to file.
     * @return a string which contains the details in a fixed format.
     */
    public String saveDetailsString() {
        String done;
        if (this.isDone) {
            done = "1";
        } else {
            done = "0";
        }

        return done + "/" + description;
        //Returns string in the style of "1/read book"
    }

    /**
     * Marks the task as done. This occurs one-way; the task cannot be unmarked.
     */
    public void markAsDone() {
        this.isDone = true;
    }


    /**
     * toString method overridden to return the a description string.
     * This string presents the task information in a readable format.
     * @return the task details.
     */
    @Override
    public String toString() {

        return getStatusIcon() + " " + description; //eg. [✓] read book
    }
}