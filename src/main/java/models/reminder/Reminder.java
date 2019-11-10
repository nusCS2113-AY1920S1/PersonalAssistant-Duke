package models.reminder;

import java.util.Date;

public class Reminder implements IReminder {
    private String reminderName;
    private String reminderRemarks;
    private Date reminderDate;
    private Boolean isDone;
    private String category;

    /**
     * Class representing a reminder in the project.
     * @param reminderName The name of the reminder.
     * @param reminderRemarks Additional information about the reminder. [Optional]
     * @param reminderDate The date that the reminder is due. [Optional]
     */
    public Reminder(String reminderName, String reminderRemarks, Date reminderDate, String category) {
        this.reminderName = reminderName;
        this.reminderRemarks = reminderRemarks;
        this.reminderDate = reminderDate;
        this.category = category;
        this.isDone = false;
    }

    @Override
    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    @Override
    public void setReminderRemarks(String reminderRemarks) {
        this.reminderRemarks = reminderRemarks;
    }

    @Override
    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    @Override
    public void setIsDone(Boolean isDone) {
        this.isDone =  isDone;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getReminderName() {
        return reminderName;
    }

    @Override
    public String getReminderRemarks() {
        return reminderRemarks;
    }

    @Override
    public Date getReminderDate() {
        return reminderDate;
    }

    @Override
    public Boolean getIsDone() {
        return this.isDone;
    }

    @Override
    public String getStatus() {
        return (this.isDone ? "[/]" : "[X]");
    }

    @Override
    public String getCategory() {
        return this.category;
    }
}
