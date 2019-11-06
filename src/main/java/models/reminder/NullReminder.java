package models.reminder;

import java.util.Date;

public class NullReminder implements IReminder {

    @Override
    public void setReminderName(String reminderName) {
        /**
         * Empty method
         */
    }

    @Override
    public void setReminderRemarks(String reminderRemarks) {
        /**
         * Empty method
         */
    }

    @Override
    public void setReminderDate(Date reminderDate) {
        /**
         * Empty method
         */
    }

    @Override
    public void setIsDone(Boolean isDone) {
        /**
         * Empty method
         */
    }

    @Override
    public String getReminderName() {
        return null;
    }

    @Override
    public String getReminderRemarks() {
        return null;
    }

    @Override
    public Date getReminderDate() {
        return null;
    }

    @Override
    public Boolean getIsDone() {
        return null;
    }

    @Override
    public String getStatus() {
        return null;
    }
}
