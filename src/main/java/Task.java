/**
 * Represents a task object containing task details.
 * @author Zhang Yue Han
 */
public class Task {

    /**
     * The task description following the task command word
     */
    protected String description;

    /**
     * The task type specified
     */
    protected String type;

    /**
     * The date of the task as specified
     */
    protected String date;

    /**
     * The boolean value to indicate if task has been marked done or not
     */
    protected boolean isDone;

    /**
     * Assigns the task description specified and sets the default value of done to false
     * @param description task name to be displayed
     */
    Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the task
     * @return value of description attribute of the task
     */
    public String getDescription() { return description; }

    /**
     * Returns the task type
     * @return value of task type: T, E or D
     */
    public String getType() { return type; }

    /**
     * Returns the date value of task
     * @return value of date in the task
     */
    public String getDate() { return date; }

    /**
     * Returns the ascii value of the tick or cross icon as indicated by the isDone bool
     * @return ascii value of status
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Returns 1 if isDone is true and 0 if isDone is false
     * @return
     */
    public int getStatus() { return (isDone ? 1 : 0); }

    /**
     * Assigns a value of true to the isDone attribute
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Converts the task to string format. Used in ui displays
     * @return string in the following format: [T][\u2713] task description
     */
    public String toString() {
        return "[" + getType() + "]" + "[" + getStatusIcon() + "] " + getDescription();
    }

    /**
     * Converts the task to data format as stored in the data file
     * @return string in the following format: T | 0 | description
     */
    public String toData() {
        return getType() + " | " + getStatus() + " | " + getDescription();
    }

    public void modifyDate(String date){
        this.date = date;
    }
}
