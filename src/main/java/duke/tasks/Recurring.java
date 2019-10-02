package duke.tasks;

import duke.DateTime;
import java.util.Date;

public class Recurring extends Task {
    private long intervalMinutes;
    private long instanceCount;

    /**
     * Constructor that is used for creation.
     * @param description the description of the recurring task.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     * @param intervalMinutes Number of minutes to advance by for next instance.
     */
    public Recurring(String description, Date startDate, Date endDate,
                     long intervalMinutes) {
        super(description);
        this.startDate = new DateTime(startDate);
        this.endDate = new DateTime(endDate);
        this.intervalMinutes = intervalMinutes;
        this.instanceCount = 1;
    }

    /**
     * Constructor that is used for recreation from storage.
     * @param description the description of the recurring task.
     * @param startDate Date object for start DateTime.
     * @param endDate Date object for end DateTime.
     * @param intervalMinutes Number of minutes to advance by for next instance.
     * @param instanceCount the number of instances completed.
     */
    public Recurring(String description, Date startDate, Date endDate,
                     long intervalMinutes, long instanceCount) {
        super(description);
        this.startDate = new DateTime(startDate);
        this.endDate = new DateTime(endDate);
        this.intervalMinutes = intervalMinutes;
        this.instanceCount = instanceCount;
    }

    @Override
    public String storeString() {
        return "R | " + super.storeString() + " | " + this.getStartDateString() + " | " + this.getEndDateString()
                + " | " + this.getIntervalMinutes() + " | " + this.getInstanceCount();
    }

    @Override
    public String toString() {
        return "[R]" + super.toString() + " (at: " + this.getStartDateString() + " to " + this.getEndDateString()
                + " every " + this.getIntervalMinutes() + " minutes" + ")";
    }

    @Override
    public void markDone() {
        long start = getStartDate().getDateTime().getTime();
        long end = getEndDate().getDateTime().getTime();

        this.setStartDate(new DateTime(new Date(start + this.millisecondsToAdd())));
        this.setEndDate(new DateTime(new Date(end + this.millisecondsToAdd())));
        this.setInstanceCount(this.getInstanceCount() + 1);
    }

    @Override
    String getStartDateString() {
        return this.startDate.toString();
    }

    @Override
    String getEndDateString() {
        return this.endDate.toString();
    }

    @Override
    protected String getStatusIcon() {
        return Long.toString(instanceCount);
    }

    private long millisecondsToAdd() {
        return this.getIntervalMinutes() * 60 * 1000;
    }

    public long getIntervalMinutes() {
        return intervalMinutes;
    }

    public long getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(long instanceCount) {
        this.instanceCount = instanceCount;
    }
}
