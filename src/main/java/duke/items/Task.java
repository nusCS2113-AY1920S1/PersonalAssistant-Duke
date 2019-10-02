package duke.items;

import java.util.Calendar;
import java.util.Date;
import duke.enums.TaskType;

/**
 * Task is an abstract class that stores the description and done status of a task.
 * Is extended by Todo, Deadline and Event classes.
 */

public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Task constructor sets a blank task by default.
     */
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
        return (isDone ? "[/] " : "[X] "); //return tick or X symbols
    }

    public boolean getIsDone() {
        return isDone;
    }

    // TODO: Deprecated, remove in future commit
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
        return taskType + "/" + done + "/" + description;
        //Returns string in the style of "T/1/read book"
    }

    /**
     * Marks the task as done. This occurs one-way; the task cannot be unmarked.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    //    public Date getDate() {
    //        return null;
    //    }

    public Calendar getDate() {
        return null;
    }

    public Calendar getEndDate() {
        return null;
    }


    /**
     * Formats all task details appropriately for Ui output.
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

        return taskType + getStatusIcon() + description; //eg. [✓] read book
    }
}