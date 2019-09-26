package seedu.duke.task;

/**
 * ToDo class is a rather basic type of Task, which has only name and isDone status, without any date/time
 * involved.
 */
public class ToDo extends Task {
    /**
     * Instantiates the ToDo class, which only takes in the name and set isDone flag to false like all tasks.
     *
     * @param name name of the task
     */
    public ToDo(String name) {
        super(name);
        this.taskType = TaskType.ToDo;
    }

    /**
     * Instantiates the ToDo class, which only takes in the name and set isDone flag to false like all tasks.
     * This method also allows a doAfter task to be entered.
     *
     * @param name    name of the task
     * @param doAfter task to be done after the main task
     */
    public ToDo(String name, String doAfter) {
        super(name);
        setDoAfterDescription(doAfter);
        this.taskType = TaskType.ToDo;
    }

    /**
     * Converts the task to a human readable string containing important information about the ToDo, including
     * the type of this task.
     *
     * @return a human readable string containing the important information
     */
    @Override
    public String toString() {
        if (doAfterDescription == null) {
            return "[T]" + this.getStatus();
        } else {
            return "[T]" + this.getStatus() + "\n   After which: " + doAfterDescription;
        }
    }

    /**
     * Outputs a string with all the information of this ToDo to be stored in a file for future usage.
     *
     * @return a string containing all information of this ToDo
     */
    @Override
    public String toFileString() {
        if (doAfterDescription == null) {
            return (this.isDone ? "1" : "0") + " todo " + this.name;
        } else {
            return (this.isDone ? "1" : "0") + " todo " + this.name + " /doafter " + this.doAfterDescription;
        }

    }
}
