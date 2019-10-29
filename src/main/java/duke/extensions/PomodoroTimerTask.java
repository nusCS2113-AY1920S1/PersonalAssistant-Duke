package duke.extensions;

import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimerTask extends TimerTask {
    private Timer t;
    private int minutesRemaining;

    public PomodoroTimerTask(Timer t, int minutesRemaining) {
        this.minutesRemaining = minutesRemaining;
        this.t = t;
    }

    public int getMinutesRemaining() {
        return minutesRemaining;
    }

    public void run() {
        minutesRemaining -= 1;

        if (minutesRemaining % 5 == 0) {
            System.out.println(minutesRemaining + " minutes left");
        }
        if (minutesRemaining == 0) {
            System.out.println("Pomodoro completed");
            t.cancel();
        }

    }
}
