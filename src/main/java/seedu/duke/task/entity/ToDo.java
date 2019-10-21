package seedu.duke.task.entity;

import java.util.ArrayList;

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
     * @param name     name of the task
     * @param doAfter  task to be done after the main task
     * @param tags     tag associated with the task
     * @param priority priority level of the task
     */
    public ToDo(String name, String doAfter, ArrayList<String> tags, String priority) {
        super(name);
        setDoAfterDescription(doAfter);
        this.taskType = TaskType.ToDo;
        setTags(tags);
        setPriorityTo(priority);
    }

    /**
     * Converts the task to a human readable string containing important information about the ToDo, including
     * the type of this task.
     *
     * @return a human readable string containing the important information
     */
    @Override
    public String toString() {
        String output = "";
        output = "[T]" + this.getStatus();
        if (this.doAfterDescription != null && !this.doAfterDescription.equals("")) {
            output += "\n\tAfter which: " + doAfterDescription;
        }
        for (String tagName : tags) {
            output += " #" + tagName;
        }
        if (this.priority != null && !this.priority.equals("")) {
            output += " Priority: " + priority;
        }
        return output;
    }

    /**
     * Outputs a string with all the information of this ToDo to be stored in a file for future usage.
     *
     * @return a string containing all information of this ToDo
     */
    @Override
    public String toFileString() {
        String output = "";
        output = (this.isDone ? "1" : "0") + " todo " + this.name;
        if (this.doAfterDescription != null && !this.doAfterDescription.equals("")) {
            output += " -doafter " + doAfterDescription;
        }
        for (String tagName : tags) {
            output += " -tag " + tagName;
        }
        if (this.priority != null && !this.priority.equals("")) {
            output += " -priority " + priority;
        }
        return output;
    }
}
