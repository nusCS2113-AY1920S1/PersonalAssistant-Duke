package duke.task;

import java.util.ArrayList;


/**
 * Represents a task.  Task is an abstract class that can not be
 * instantiated
 */
public abstract class Task {

    /**
     * A String that represents the description of the task.
     */
    private String description;

    /**
     * A boolean that represents the status of the task( 1 means done, 0 means not yet).
     */
    private boolean isDone;

    /**
     * A string to describe the date/time of the task.
     */
    private String dateTime = "";

    /**
     * An arraylist of things the nurse can bring for the task.
     */
    private ArrayList<String> thingsToBring;

    /**
     * Initialises the minimum fields required to setup a Task.
     *
     * @param description A string that represents the description of certain task.
     */
    public Task(String description, String dateTime) {
        this.description = description;
        this.isDone = false;
        this.thingsToBring = new ArrayList<String>();
        this.dateTime = dateTime;
    }

    public Task(String description) {
        this.description = description;
        this.thingsToBring = new ArrayList<String>();
        this.isDone = false;
    }

    /**
     * Returns an icon that represents the status of the task.
     *
     * @return Tick if completed, cross if uncompleted.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Check if the task isDone.
     *
     * @return boolean value of isDone
     */
    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

//    /**
//     * Returns a string with the following format to be stored in a local file.
//     *
//     * @return A string in a specific format to be stored in a local file.
//     */
//    public abstract String writeTxt();

    /**
     * Returns a string with the status icon and the description of the task.
     *
     * @return A string in a specific format with the status and description of the task.
     */
    public String printStatus() {
        return "[" + this.getStatusIcon() + "] " + description;
    }

    /**
     * Returns the description of the task.
     *
     * @return A string that represents the specific activity associated with
     *         the task.
     */

    public String getDescription() {
        return description;
    }

    /**
     * update the dateTime String to save the date and time.
     * @param newDateTime the time retrieved from user input.
     */

    public void updateDateTime(String newDateTime) {
        this.dateTime = newDateTime;
    }

    /**
     * Returns the dateTime String.
     */

    public String getDateTime() {
        return dateTime;
    }

    public abstract String toString();

}