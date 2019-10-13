package duke.task.duketasks;

import duke.task.Task;

import java.util.Date;

public class Period extends Task {

    protected String startDate;
    protected String endDate;

    /**
     * Constructor for class Period.
     * @param description String containing the description of the task
     * @param startDate String containing the initial date that a task should be done.
     * @param endDate String containing the last date that a task should be done.
     */
    public Period(String description, String startDate, String endDate) {
        super(description);//super class constructor call to the Task(description) constructor
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Converts user input command to a standardized format to store in file.
     * @return String containing the standardized format
     */
    @Override
    public String toSaveString() {
        return "P" + super.toSaveString() + " | " + startDate + " | " + endDate;
    }

    /**
     * Converts user input command to a standardized format in taskList.
     * @return String containing the standardized format
     */
    @Override
    public String toString() {
        return "[P]" + super.toString() + " (between: " + startDate + " and " + endDate + ")";
    }

    public Date getDateTime() {
        return null;
    }
}