package Model_Classes;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;

public class TaskReminder {
    private Timer timer = new Timer();
    private int duration;
    private String description;

    /**
     * constructor for the TaskReminder class.
     * @param description description of the reminder
     * @param duration duration of the reminder
     */
    public TaskReminder(String description, int duration) {
        this.duration = duration;
        this.description = description;
    }

    /**
     * schedules a timer to play a sound when the time is up.
     */
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
