package models.reminder;

import java.util.Date;

public class Reminder implements IReminder {
    private String reminderName;
    private Date reminderDate;

    public Reminder(String reminderName, Date reminderDate) {
        this.reminderName = reminderName;
        this.reminderDate = reminderDate;
    }

    @Override
    public void setReminderName(String reminderName) {
        this.reminderName = reminderName;
    }

    @Override
    public void setReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
    }

    @Override
    public String getReminderName() {
        return reminderName;
    }

    @Override
    public Date getReminderDate() {
        return reminderDate;
    }
}
