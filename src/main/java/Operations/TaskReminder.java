package Operations;

import CustomExceptions.RoomShareException;
import Enums.Priority;
import Enums.RecurrenceScheduleType;
import Enums.TimeUnit;
import Model_Classes.Task;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;

public class TaskReminder {
    private Timer timer = new Timer();
    private int duration;
    private String description;

    public TaskReminder() {
    }

    public TaskReminder(String description, int duration) {
        this.duration = duration;
        this.description = description;
    }

    public void start() {
        timer.schedule(new TimerTask() {
            public void run() {
                playSound();
                timer.cancel();
            }
            private void playSound() {
                System.out.println(description + " is completed!!");
                Toolkit.getDefaultToolkit().beep();
            }
        }, duration * 1000);
    }
}
