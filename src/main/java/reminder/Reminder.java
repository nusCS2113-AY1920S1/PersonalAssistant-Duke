package reminder;

import java.util.Date;
import java.util.Timer;

public class Reminder {
    Timer timer;

    public Reminder(Date date) {
        timer = new Timer();
        timer.schedule(new RemindTask(timer), date);
    }
}
