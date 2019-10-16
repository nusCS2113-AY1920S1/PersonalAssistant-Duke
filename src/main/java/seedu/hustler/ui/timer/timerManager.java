package seedu.hustler.ui.timer;

import seedu.hustler.ui.timer.statusTypes.threadStatus;
import seedu.hustler.ui.timer.statusTypes.threadError;

/**
 * The access point for all timer-related things.
 */
public class timerManager {

    /**
     * All instances of timerManager share the same timer and
     * thread so that all things timer-related are in sync.
     */
    protected static timer countdownTimer;
    protected static Thread countdownThread;

    /**
     * Empty constructor that also creates a timer and a thread
     * for the timeManager. All timeManagers are required to
     * have these two attributes initialised.
     */
    public timerManager() {
	    countdownTimer = new timer();
            countdownThread = new Thread(countdownTimer);
    }

    /**
     * Allows the timeManager to set the timer's time as per
     * the user's wishes.
     */
    public void setTimer(String time) {
        String[] timeParts = time.split(" ");
        countdownTimer = new timer(timeParts[0], timeParts[1], timeParts[2]);
    }

    /**
     * Allows the timeManager to reset the timer, reverting
     * its hours, minutes and seconds to 0 again at any
     * point.
     */
    public static void resetTimer() {
        countdownTimer = new timer();
        timer.threadstatus = threadStatus.RESET;
    }

    /**
     * Allows the timeManager to start the timer countdown,
     * usually done after setting the timer.
     */
    public void startTimer() {
        timer.threadstatus = threadStatus.RUNNING;
        countdownThread = new Thread(countdownTimer);
        countdownThread.start();
    }

    /**
     * Allows the timeManager to retrieve the current time
     * of the timer in order to print and inform the user.
     * Lets the user know how much time is left.
     */
    public void checkTimeLeft() {
        timerUI.printTimeLeft(countdownTimer.getTime());
    }

    /**
     * Allows the timeManager to pause the timer if the
     * user so desires.
     */
    public static void pauseTimer() {
        if (timer.threadstatus == threadStatus.RUNNING || timer.threadstatus == threadStatus.RESUMED) {
            timer.threadstatus = threadStatus.PAUSED;
            countdownThread.interrupt();
        } else {
            timerUI.printThreadError(threadError.PAUSEERROR);
        }
    }

    /**
     * Allows the timeManager to resume a timer that has
     * been paused by the user. Timers that have not been
     * paused cannot be resumed.
     */
    public static void resumeTimer() {
        if (timer.threadstatus == threadStatus.PAUSED) {
            timer.threadstatus = threadStatus.RESUMED;
            countdownThread = new Thread(countdownTimer);
            countdownThread.start();
        } else {
            timerUI.printThreadError(threadError.RESUMEERROR);
        }
    }

    /**
     * Allows the timeManager to stop a timer's countdown
     * prematurely.
     */
    public static void stopTimer() {
        if (timer.threadstatus == threadStatus.RUNNING || timer.threadstatus == threadStatus.RESUMED || timer.threadstatus == threadStatus.PAUSED) {
            timer.threadstatus = threadStatus.STOPPED;
            countdownThread.interrupt();
            resetTimer();
        } else {
            timerUI.printThreadError(threadError.STOPERROR);
        }
    }
}
