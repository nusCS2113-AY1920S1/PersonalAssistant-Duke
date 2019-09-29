package duke.task;

import java.util.Date;

public class DoAfter extends Task {

    protected String after;

    /**
     * Constructor for class DoAfter.
     * @param description String containing the description of the task
     * @param after String containing the time or tasks to be met or done
     */
    public DoAfter(String description, String after) {
        super(description);//super class constructor call to the Task(description) constructor
        this.after = after;
    }

    /**
     * Converts user input command to a standardized format to store in file.
     * @return String containing the standardized format
     */
    @Override
    public String toSaveString() {
        return "T" + super.toSaveString() + " | " + after;
    }

    /**
     * Converts user input command to a standardized format in taskList.
     * @return String containing the standardized format
     */
    @Override
    public String toString() {
        return "[T]" + super.toString() + " (after: " + after + ")";
    }

    public Date getDateTime() {
        return null;
    }
}