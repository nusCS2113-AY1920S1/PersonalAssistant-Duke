package seedu.hustler.ui.timer;

import seedu.hustler.ui.timer.statusTypes.threadError;
import seedu.hustler.ui.timer.statusTypes.threadStatus;

/**
 * Manages all code that will need to print information for the user.
 * Acts as a node and HQ for all such user-interface activities.
 */
public class timerUI {

    /**
     * All strings to be printed will always be generated elsewhere. This private
     * function will be the last part of every printing-related action; this method
     * is the gate of sorts. This deliberate abstraction allows the developer
     * to mute printing if required.
     */
    private static void printToUser (String output) {
        System.out.println(output);
    }

    /**
     * The main location to generate messages related to why a timer starts to
     * countdown (new timer started, old timer resumed, etc).
     */
    protected static void printThreadStart(threadStatus threadstatus, int[] timeArray) {
        if (threadstatus == threadStatus.RUNNING) {
            printToUser(UIMessages.threadStart.TimerStart(timeArray));
	} else if (threadstatus == threadStatus.RESUMED) {
            printToUser(UIMessages.threadStart.TimerResumed());
        }
    }

    /**
     * The main location to generate messages related to why a timer has
     * stopped its countdown (has it been paused, stopped prematurely, etc).
     */
    protected static void printThreadInterrupt(threadStatus threadstatus) {
        if (threadstatus == threadStatus.PAUSED) {
            printToUser(UIMessages.threadStop.TimerPaused());
        } else if (threadstatus == threadStatus.RESET) {
            printToUser(UIMessages.threadStop.TimerStopped());
        } else if (threadstatus == threadStatus.RUNNING || threadstatus == threadStatus.RESUMED) {
            printToUser(UIMessages.threadStop.TimesUp());
            timer.threadstatus = threadStatus.FINISHED;
        }
    }

    /**
     * The main location to generate messages related to all errors that arise
     * to the the timer/ thread (invalid commands, etc). 
     */
    protected static void printThreadError(threadError threaderrortype) {
        if (threaderrortype == threadError.RESUMEERROR) {
            printToUser(UIMessages.threadError.NoTimerToResumeError());
        } else if (threaderrortype == threadError.PAUSEERROR) {
            printToUser(UIMessages.threadError.NoTimerToPauseError());
        } else if (threaderrortype == threadError.STOPERROR) {
            printToUser(UIMessages.threadError.NoTimerToStopError());
        }
    }

    /**
     * Generates the string the inform the user of how much time remains.
     */
    protected static void printTimeLeft(int[] timeArray) {
        String output = "Time remaining: " + UILogic.padOutput(timeArray);
        printToUser(output);
    }
}
