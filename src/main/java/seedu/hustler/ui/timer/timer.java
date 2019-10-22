package seedu.hustler.ui.timer;

/**
 * The timer device operated by timerManager.
 */
public class timer implements Runnable {
    /**
     * The current status of the timer, an attribute that is
     * affected by the types of commands used by the user.
     */
    protected static threadStatus threadstatus = threadStatus.DEFAULT;

    /**
     * An array of 3 integers representing the hours, minutes
     * and seconds (indexes 2, 1, and 0 respectively of timeArray)
     * of the timer.
     */
    protected int[] timeArray = new int[3];

    /**
     * Default constructor that initialises the timer to 0hrs
     * 0minutes and 0seconds.
     */
    protected timer() {
        timeArray = new int[3];
	    threadstatus = threadStatus.DEFAULT;
    }

    /**
     * Overloaded constructor that directly sets the hours, minutes
     * and seconds of the timer according to the user's discretion.
     *
     * @param hours hours of the timer.
     * @param minutes minutes of the timer.
     * @param seconds seconds of the timer.
     */
    protected timer(String hours, String minutes, String seconds) {
        timeArray[0] = Integer.parseInt(seconds);
        timeArray[1] = Integer.parseInt(minutes);
        timeArray[2] = Integer.parseInt(hours);
    }

    /**
     * Types of statuses the timer can have.
     */
    protected enum threadStatus {
        DEFAULT,
	    RUNNING,
	    PAUSED,
        RESUMED,
        RESET,
        STOPPED,
        FINISHED
    }

    /**
     * Starts running the timer, counting down until
     * the time is up or if the timer is ended
     * prematurely by the user. Timer can be paused
     * and resumed.
     */
    public void run() {
        timerUI.printThreadStart(threadstatus, timeArray);
        try {
            while (!isFinished()) {
                Thread.sleep(1000);
                timeArray = timerLogic.decrement(timeArray);
            }
        } catch (Exception e) {
        }
        timerUI.printThreadInterrupt(threadstatus);
    }

    /**
     * Returns the time currently stored by the timer.
     * This method is an accessor for other methods.
     *
     * @return the entire array containing information
     * about the timer's hours, minutes and seconds.
     */
    protected int[] getTime() {
        return timeArray;
    }

    /**
     * @return a boolean value telling the timer if
     * the countdown is complete. The countdown is
     * complete if all 3 values of hours, minutes
     * and seconds are 0.
     */
    private boolean isFinished() {
        return (timeArray[0] == 0 && timeArray[1] == 0 && timeArray[2] == 0);
    }
}
