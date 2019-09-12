package myTasks;

/**
 * The task superclass.
 * It contains the description and task status.
 * The task number is specific handled by the task list.
 * It does not contain any dates or times; These are specific only to deadline and event tasks.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructor of the task object.
     * It only takes in the description of the task.
     * The status of the task defaults to not completed.
     *
     * @param description The description of the task.
     */
    public Task(String description) { //initialization
        this.description = description;
        this.isDone = false;
    }

    /**
     * Method to get the status of the object.
     * Overwritten be the same method in the deadline and even tasks.
     *
     * @return Y if the task is done. N if the task is not done.
     */
    public String getStatus() {

        return "[" + (isDone ? "Y" : "N") + "]"; //return tick or X symbols in a bracket
    }


    /**
     * Method to get the status of the task as a single digit and string.
     * Primarily used for easier storage of the task in the save file.
     *
     * @return 1 if the task is done. 0 if the task is not done.
     */
    //Return status icon as int, for easier reading when saving
    public String getStatusInt() {
        return isDone ? "1" : "0";
    }

    /**
     * Method to update the status of the task from not done to done.
     */
    public void markAsDone() { //marks a task as done

        this.isDone = true;
    }

    /**
     * Method to return the type of task.
     * Overwritten by its children classes deadline, event and todoo.
     *
     * @return The type of task, in this case it defaults to Task.
     */
    public String getType() { //returns type of task, to be overwritten
        return "Task";
    }

    /**
     * Method to return the date of the task.
     * Overwritten by its children classes deadline and event.
     *
     * @return The word date a string by default. It is overwritten.
     */
    public String getBy() { //returns deadline or event date, to be overwritten
        return "Date";
    }

    /**
     * Method to return the full task data, including its status and date.
     * This is overwritten in its children classes.
     *
     * @return The word Full_description as a string.
     */
    public String getStatusIcon() {
        return "Full_description"; //returns the full task data, to be overwritten
    }

    /**
     * Method to return the task description.
     *
     * @return The task's description as a string.
     */
    public String getDescription() {

        return this.description;
    }

}
