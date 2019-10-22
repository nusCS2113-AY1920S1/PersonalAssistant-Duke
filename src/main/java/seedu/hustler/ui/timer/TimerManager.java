package seedu.hustler.ui.timer;

import seedu.hustler.ui.timer.statusTypes.threadStatus;
import seedu.hustler.ui.timer.statusTypes.threadError;


/**
 * The access point for all Timer-related things.
 */
public class TimerManager {

    /**
     * All instances of TimerManager share the same Timer and
     * thread so that all things Timer-related are in sync.
     */
    protected static Timer countdownTimer;
    protected static Thread countdownThread;

    /**
     * Empty constructor that also creates a Timer and a thread
     * for the timeManager. All timeManagers are required to
     * have these two attributes initialised.
     */
    public TimerManager() {
        countdownTimer = new Timer();
        countdownThread = new Thread(countdownTimer);
    }

    /**
     * Allows the timeManager to set the Timer's time as per
     * the user's wishes.
     */
    public void setTimer(String time) {
        String[] timeParts = time.split(" ");
        countdownTimer = new Timer(timeParts[0], timeParts[1], timeParts[2]);
    }

    /**
     * Allows the timeManager to reset the Timer, reverting
     * its hours, minutes and seconds to 0 again at any
     * point.
     */
    public static void resetTimer() {
        countdownTimer = new Timer();
        Timer.threadstatus = threadStatus.RESET;
    }

    /**
     * Allows the timeManager to start the Timer countdown,
     * usually done after setting the Timer.
     */
    public void startTimer() {
        Timer.threadstatus = threadStatus.RUNNING;
        countdownThread = new Thread(countdownTimer);
        countdownThread.start();
    }

    /**
     * Allows the timeManager to retrieve the current time
     * of the Timer in order to print and inform the user.
     * Lets the user know how much time is left.
     */
    public void checkTimeLeft() {
        TimerUI.printTimeLeft(countdownTimer.getTime());
    }

    /**
     * Allows the timeManager to pause the Timer if the
     * user so desires.
     */
    public static void pauseTimer() {
        if (Timer.threadstatus == threadStatus.RUNNING || Timer.threadstatus == threadStatus.RESUMED) {
            Timer.threadstatus = threadStatus.PAUSED;
            countdownThread.interrupt();
        } else {
            TimerUI.printThreadError(threadError.PAUSEERROR);
        }
    }

    /**
     * Allows the timeManager to resume a Timer that has
     * been paused by the user. Timers that have not been
     * paused cannot be resumed.
     */
    public static void resumeTimer() {
        if (Timer.threadstatus == threadStatus.PAUSED) {
            Timer.threadstatus = threadStatus.RESUMED;
            countdownThread = new Thread(countdownTimer);
            countdownThread.start();
        } else {
            TimerUI.printThreadError(threadError.RESUMEERROR);
        }
    }

    /**
     * Allows the timeManager to stop a Timer's countdown
     * prematurely.
     */
    public static void stopTimer() {
        if (Timer.threadstatus == threadStatus.RUNNING || Timer.threadstatus == threadStatus.RESUMED || Timer.threadstatus == threadStatus.PAUSED) {
            Timer.threadstatus = threadStatus.STOPPED;
            countdownThread.interrupt();
            resetTimer();
        } else {
            TimerUI.printThreadError(threadError.STOPERROR);
        }
    }
}
