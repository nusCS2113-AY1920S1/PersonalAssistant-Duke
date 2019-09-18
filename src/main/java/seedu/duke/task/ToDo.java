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
        return (this.isDone ? "1" : "0") + " todo " + this.name;
    }
}
