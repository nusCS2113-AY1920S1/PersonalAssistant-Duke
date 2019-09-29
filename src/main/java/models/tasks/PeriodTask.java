package models.tasks;

import java.util.Date;

public class PeriodTask implements ITask {
    private boolean isDone;
    private String initials;
    private String description;
    private String taskDuration;
    private Date startDate;
    private Date endDate;


    /**
     * Constructor of the Recurring data model.
     *
     * @param description : Description of the new period task
     * @param startDate : Start date of this period Task
     * @param endDate : End date of this period Task
     */
    public PeriodTask(String description, String taskDuration,Date startDate, Date endDate) {
        this.isDone = false;
        this.initials = "P";
        this.description = description;
        this.taskDuration = taskDuration;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗");
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String getDescription() {
        return this.description + " between " + taskDuration;
    }

    @Override
    public String getInitials() {
        return this.initials;
    }

    @Override
    public String getDateTime() {
        return null;
    }

    @Override
    public Date getDateTimeObject() {
        return null;
    }

    @Override
    public void setDateTime(String newDateTime) {

    }
}
