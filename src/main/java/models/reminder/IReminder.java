package models.reminder;

import java.util.Date;

public interface IReminder {

    void setReminderName(String reminderName);

    void setReminderRemarks(String reminderRemarks);

    void setReminderDate(Date reminderDate);

    void setIsDone(Boolean isDone);

    String getReminderName();

    String getReminderRemarks();

    Date getReminderDate();

    Boolean getIsDone();

    String getStatus();
}
