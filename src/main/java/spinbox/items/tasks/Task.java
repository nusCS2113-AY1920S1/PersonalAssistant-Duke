package spinbox.items.tasks;

import spinbox.DateTime;
import spinbox.items.Item;

public abstract class Task extends Item {
    DateTime startDate;
    DateTime endDate;

    /**
     * Constructor to initialize default values of any instances of children of Task.
     */
    public Task(String taskName) {
        super(taskName);
        this.startDate = null;
        this.endDate = null;
    }

    @Override
    public String storeString() {
        return super.storeString();
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
    public Boolean isOverlapping(DateTime startTime, DateTime endTime) {
        return false;
    }
}
