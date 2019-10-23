package seedu.hustler.ui.timer;

/**
 * This class contains information on the how to actually decrement the
 * timer's time. Allows the timer to actually countdown.
 */
public class TimerLogic {

    /**
     * @param time[] takes the current time of the timer and attempts to
     * decrement it by one second.
     * @return the next iteration of the countdown.
     */
    protected static int[] decrement(int time[]) {
        int index = 0;
        return recursiveDecrement(index, time);
    }

    /**
     * Recursively countsdown the timer's time. If seconds if 0, reduce
     * minutes, if minutes if 0, reduce hours etc.
     */
    private static int[] recursiveDecrement(int index, int time[]) {
        if (time[index] == 0) {
            time[index] = 59;
            return recursiveDecrement(index + 1, time);
        } else {
            time[index]--;
            return time;
        }
    }
}