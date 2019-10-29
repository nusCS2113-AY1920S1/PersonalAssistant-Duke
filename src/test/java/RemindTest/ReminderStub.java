package RemindTest;

import Commons.Reminder;
import Tasks.Assignment;
import Tasks.Deadline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ReminderStub extends Reminder {
    private HashMap<Date, Assignment> remindMap = new HashMap<>();
    private SimpleDateFormat dateOutputFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private SimpleDateFormat timeOutputFormat = new SimpleDateFormat("hh:mm a");
    private static Assignment reminderTask;
    private static Date reminderTime;

    @Override
    public void removeTimerTask(Assignment task, Date date, String reminderTime) {
        assert task != null;
        assert date != null;
        assert reminderTime != null;
    }

    @Override
    public void setReminderThread(Date date, Assignment task) {
        assert date != null;
        assert task != null;
    }

    public static void setReminderTask(Assignment task) {
        reminderTask = task;
    }

    public static void setReminderTime(Date time) {
        reminderTime = time;
    }

    @Override
    public HashMap<Date, Assignment> getRemindMap() {
        remindMap.put(reminderTime, reminderTask);
        return remindMap;
    }
}
