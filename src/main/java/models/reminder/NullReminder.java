package models.reminder;

import java.util.Date;

public class NullReminder implements IReminder {

    @Override
    public void setReminderName(String reminderName) {
    }

    @Override
    public void setReminderDate(Date reminderDate) {
    }

    @Override
    public String getReminderName() {
        return null;
    }

    @Override
    public Date getReminderDate() {
        return null;
    }
}
