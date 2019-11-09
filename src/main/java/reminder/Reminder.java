package reminder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class Reminder {
    Timer timer;

    public Reminder(Date date, ArrayList<String> wordArrayList, String reminderInfo) {
        timer = new Timer();
        timer.schedule(new RemindTask(timer, wordArrayList, reminderInfo), date);
    }
}
