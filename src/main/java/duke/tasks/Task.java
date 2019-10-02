package duke.tasks;

import duke.DateTime;
import java.util.Date;

public abstract class Task {
    private String taskName;
    Boolean isDone;
    DateTime startDate;
    DateTime endDate;

    /**
     * Constructor to initialize default values of any instances of children of Task.
     */
    public Task(String taskName) {
        this.taskName = taskName;
        this.isDone = false;
        this.startDate = null;
        this.endDate = null;
    }

    protected String getStatusIcon() {
        return (isDone ? "✓" : "✗");
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getTaskName();
    }

    public String storeString() {
        return Integer.toString((isDone ? 1 : 0)) + " | " + this.getTaskName();
    }

    public String getTaskName() {
        return this.taskName;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public void markDone() {
        this.isDone = true;
    }

    public Boolean getDone() {
        return isDone;
    }

    abstract String getStartDateString();

    abstract String getEndDateString();

    /**
     * Compare if input date is equals.
     * @return default is to return false, only implemented properly in deadline and event task.
     */
    public boolean compareEquals(DateTime inputDate) {
        return false;
    }


    /**
     * Compare if time interval overlaps.
     * @param startTime the start of the interval
     * @param endTime the end of the interval
     * @return default to return false, only implemented in event task.
     */
    public Boolean isOverlapping(Date startTime, Date endTime) {
        return false;
    }
}
