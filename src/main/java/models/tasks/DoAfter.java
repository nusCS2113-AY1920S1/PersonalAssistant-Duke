package models.tasks;

import java.util.Date;

public class DoAfter implements ITask {
    private String doAfterTask;
    private String doBeforeTask;
    private String initials;
    private boolean isDone;

    /**
     * Constructor for Do After Task.
     * @param doAfterTask : The task which needs to be done before the new task is done
     * @param doBeforeTask : The new task to be done
     */
    public DoAfter(String doAfterTask, String doBeforeTask) {
        this.doAfterTask = doAfterTask;
        this.doBeforeTask = doBeforeTask;
        this.initials = "DA";
        this.isDone = false;
    }

    @Override
    public String getStatusIcon() {
        return (isDone ? "✓" : "✗"); //return tick or X symbols
    }

    @Override
    public void markAsDone() {
        this.isDone = true;
    }

    @Override
    public String getDescription() {
        if (doAfterTask == null) {
            return null;
        } else {
            return doAfterTask + " /after " + doBeforeTask;
        }
    }

    @Override
    public String getInitials() {
        return this.initials;
    }

    @Override
    public Date getDateTimeObject() {
        return null;
    }

    @Override
    public String getDateTime() {
        return null;
    }

    @Override
    public String getFullDescription() {
        return null;
    }
}
