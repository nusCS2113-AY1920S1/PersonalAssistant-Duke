package seedu.hustler.ui.timer;

import seedu.hustler.ui.timer.Timer.ThreadStatus;
import seedu.hustler.ui.timer.ThreadErrorUiMessages.ThreadErrorType;

/**
 * The access point for all timer-related things.
 */
public class TimerManager {
    /**
     * All instances of timerManager share the same timer and
     * thread so that all things timer-related are in sync.
     */
    protected static Timer countdownTimer;
    protected static Thread countdownThread;

    /**
     * Empty constructor that also creates a timer and a thread
     * for the timeManager. All timeManagers are required to
     * have these two attributes initialised.
     */
    public TimerManager() {
        countdownTimer = new Timer();
        countdownThread = new Thread(countdownTimer);
    }

    /**
     * Allows the timeManager to set the timer's time as per
     * the user's wishes.
     */
    public void setTimer(String time) {
        String[] timeParts = time.split(" ");
        countdownTimer = new Timer(timeParts[0], timeParts[1], timeParts[2]);
    }

    /**
     * Allows the timeManager to reset the timer, reverting
     * its hours, minutes and seconds to 0 again at any
     * point.
     */
    public static void resetTimer() {
        countdownTimer = new Timer();
        Timer.threadstatus = ThreadStatus.RESET;
    }

    /**
     * Allows the timeManager to start the timer countdown,
     * usually done after setting the timer.
     */
    public void startTimer() {
        Timer.threadstatus = ThreadStatus.RUNNING;
        countdownThread = new Thread(countdownTimer);
        countdownThread.start();
    }

    /**
     * Allows the timeManager to retrieve the current time
     * of the timer in order to print and inform the user.
     * Lets the user know how much time is left.
     */
    public void checkTimeLeft() {
        TimerUI.printTimeLeft(countdownTimer.getTime());
    }

    /**
     * Allows the timeManager to pause the timer if the
     * user so desires.
     */
    public static void pauseTimer() {
        if (Timer.threadstatus == ThreadStatus.RUNNING || Timer.threadstatus == ThreadStatus.RESUMED) {
            Timer.threadstatus = ThreadStatus.PAUSED;
            countdownThread.interrupt();
        } else {
            TimerUI.printThreadError(ThreadErrorType.PAUSEERROR);
        }
    }

    /**
     * Allows the timeManager to resume a timer that has
     * been paused by the user. Timers that have not been
     * paused cannot be resumed.
     */
    public static void resumeTimer() {
        if (Timer.threadstatus == ThreadStatus.PAUSED) {
            Timer.threadstatus = ThreadStatus.RESUMED;
            countdownThread = new Thread(countdownTimer);
            countdownThread.start();
        } else {
            TimerUI.printThreadError(ThreadErrorType.RESUMEERROR);
        }
    }

    /**
     * Allows the timeManager to stop a timer's countdown
     * prematurely.
     */
    public static void stopTimer() {
        if (Timer.threadstatus == ThreadStatus.RUNNING || Timer.threadstatus == ThreadStatus.RESUMED
                || Timer.threadstatus == ThreadStatus.PAUSED) {
            Timer.threadstatus = ThreadStatus.STOPPED;
            countdownThread.interrupt();
            resetTimer();
        } else {
            TimerUI.printThreadError(ThreadErrorType.STOPERROR);
        }
    }
}
