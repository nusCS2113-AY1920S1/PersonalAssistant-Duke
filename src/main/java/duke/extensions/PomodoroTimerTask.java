package duke.extensions;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer task class that takes in the amount of time to run it for and the timer
 */
public class PomodoroTimerTask extends TimerTask {
    private Timer t;
    private int minutesRemaining;

    /**
     * Constructor for the timer task
     * @param t timer to run this task on
     * @param minutesRemaining integer in minutes amount of time to run this timer for
     */
    public PomodoroTimerTask(Timer t, int minutesRemaining) {
        this.minutesRemaining = minutesRemaining;
        this.t = t;
    }

    /**
     * Gets the minutes remaining
     * @return an integer on remaining minutes
     */
    public int getMinutesRemaining() {
        return minutesRemaining;
    }

    public void setMinutesRemaining(int minutesRemaining) {
        this.minutesRemaining = minutesRemaining;
    };

    /**
     * Run method that prints out the time remaining and ends the timer task once the time is up
     */
    public void run() {
        minutesRemaining -= 1;

        if (minutesRemaining % 5 == 0) {
            System.out.println("");
            System.out.println("% " + minutesRemaining + " minutes left. Timer has "
                    + "currently been shortened for testing purposes");
        }
        if (minutesRemaining == 0) {
            System.out.println("Pomodoro completed");
            t.cancel();
        }

    }
}
