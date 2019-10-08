package models.temp.tasks;

import java.util.Date;

public class PeriodTask implements ITask {
    private boolean isDone;
    private String initials;
    private String description;
    private String taskDuration;
    
    /**
     * Constructor of the Recurring data model.
     *
     * @param description : Description of the new period task
     */
    public PeriodTask(String description, String taskDuration) {
        this.isDone = false;
        this.initials = "P";
        this.description = description;
        this.taskDuration = taskDuration;
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
    public String getFullDescription() {
        return null;
    }

    @Override
    public Date getDateTimeObject() {
        return null;
    }

    @Override
    public void setDateTime(String newDateTime) {
        // Not implemented
    }
}
