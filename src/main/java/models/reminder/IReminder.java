package models.reminder;

import java.util.Date;

public interface IReminder {

    void setReminderName(String reminderName);

    void setReminderDate(Date reminderDate);

    String getReminderName();

    Date getReminderDate();
}
