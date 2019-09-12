package duke.items;

/**
 * Task is an abstract class that stores the description and done status of a task.
 * Is extended by Todo, Deadline and Event classes.
 */

public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    protected enum TaskType {
        TODO, DEADLINE, EVENT
    }

    public Task() {
        this.description = "None";
        this.isDone = false;
    }

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

    public void printTaskDetails() {
        System.out.println(toString());
    }

    /**
     * Method that returns a string with the details for saving to file.
     * @return a string which contains the details in a fixed format.
     */
    public String saveDetailsString() {
        String done;
        if (this.isDone) done = "1";
        else done = "0";

        return done + "/" + description;
        //Returns string in the style of "1/read book"
    }

    /**
     * Marks the task as done. This occurs one-way; the task cannot be unmarked.
     * @return Nothing.
     */
    public void markAsDone() {
        this.isDone = true;
    }


    @Override
    public String toString() {
        /**
         * toString method overridden to return the a description string.
         * This string presents the task information in a readable format.
         * @return the task details.
         */
        return getStatusIcon() + " " + description; //eg. [✓] read book
    }
}