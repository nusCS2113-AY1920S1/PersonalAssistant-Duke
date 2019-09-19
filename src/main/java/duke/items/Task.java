package duke.items;

import java.util.Date;

/**
 * Task is an abstract class that stores the description and done status of a task.
 * Is extended by Todo, Deadline and Event classes.
 */

public class Task {
    protected int taskIndex;
    protected String description;
    protected boolean isDone;
    protected TaskType type;
    protected String doAfter; //Can extend to a list of tasks that have to be done before.

    protected enum TaskType {
        TODO, DEADLINE, EVENT
    }

    /**
     * Task constructor sets a blank task by default.
     */
    public Task() {
        this.taskIndex = -1;
        this.description = "None";
        this.isDone = false;
    }

    /**
     * All tasks contain a description and isDone status, and also belong to a type.
     */
    public Task(int index, String description, TaskType type, String doAfter) {
        this.taskIndex = index;
        this.description = description;
        this.isDone = false;
        this.type = type;
        this.doAfter = doAfter;
    }

    public int getTaskIndex() {
        return taskIndex;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        return (isDone ? "[\u2713] " : "[\u2718] "); //return tick or X symbols
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setDoAfter(String doAfter) {
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
        String taskType;
        if (this.isDone) {
            done = "1";
        } else {
            done = "0";
        }

        if (this.type == TaskType.DEADLINE) {
            taskType = "D";
        } else if (this.type == TaskType.TODO) {
            taskType = "T";
        } else {
            taskType = "E";
        }
        return taskIndex + "/" + taskType + "/" + done + "/" + description;
        //Returns string in the style of "12/T/1/read book"
    }

    /**
     * Marks the task as done. This occurs one-way; the task cannot be unmarked.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    public Date getDate() {
        return null;
    }


    /**
     * toString method overridden to return the a description string.
     * This string presents the task information in a readable format.
     * @return the task details.
     */
    @Override
    public String toString() {
        String taskType;
        if (this.type == TaskType.DEADLINE) {
            taskType = "[D] ";
        } else if (this.type == TaskType.TODO) {
            taskType = "[T] ";
        } else {
            taskType = "[E] ";
        }

        String after;
        if (!doAfter.equals("")) {
            after = " (do after: " + doAfter + ")";
        } else {
            after = "";
        }

        return taskType + getStatusIcon() + description + after; //eg. [✓] read book
    }
}