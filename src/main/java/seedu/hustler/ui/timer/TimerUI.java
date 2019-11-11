package seedu.hustler.ui.timer;

import seedu.hustler.ui.timer.StatusTypes.ThreadError;
import seedu.hustler.ui.timer.StatusTypes.ThreadStatus;

/**
 * Manages all code that will need to print information for the user.
 * Acts as a node and HQ for all such user-interface activities.
 */
public class TimerUI {

    /**
     * All strings to be printed will always be generated elsewhere. This private
     * function will be the last part of every printing-related action; this method
     * is the gate of sorts. This deliberate abstraction allows the developer
     * to mute printing if required.
     */
    private static void printToUser(String output) {
        System.out.println(output);
    }

    /**
     * The main location to generate messages related to why a timer starts to
     * countdown (new timer started, old timer resumed, etc).
     */
    protected static void printThreadStart(ThreadStatus threadstatus, int[] timeArray) {
        if (threadstatus == ThreadStatus.RUNNING) {
            printToUser(UiMessages.ThreadStart.timerStart(timeArray));
        } else if (threadstatus == ThreadStatus.RESUMED) {
            printToUser(UiMessages.ThreadStart.timerResumed());
        }
    }

    /**
     * The main location to generate messages related to why a timer has
     * stopped its countdown (has it been paused, stopped prematurely, etc).
     */
    protected static void printThreadInterrupt(ThreadStatus threadstatus) {
        if (threadstatus == ThreadStatus.PAUSED) {
            printToUser(UiMessages.ThreadStop.timerPaused());
        } else if (threadstatus == ThreadStatus.RESET) {
            printToUser(UiMessages.ThreadStop.timerStopped());
        } else if (threadstatus == ThreadStatus.RUNNING || threadstatus == ThreadStatus.RESUMED) {
            printToUser(UiMessages.ThreadStop.timesUp());
            Timer.threadstatus = ThreadStatus.FINISHED;
        }
    }

    /**
     * The main location to generate messages related to all errors that arise
     * to the the timer/ thread (invalid commands, etc).
     */
    protected static void printThreadError(ThreadError threaderrortype) {
        if (threaderrortype == ThreadError.RESUMEERROR) {
            printToUser(UiMessages.ThreadError.noTimerToResumeError());
        } else if (threaderrortype == ThreadError.PAUSEERROR) {
            printToUser(UiMessages.ThreadError.noTimerToPauseError());
        } else if (threaderrortype == ThreadError.STOPERROR) {
            printToUser(UiMessages.ThreadError.noTimerToStopError());
        }
    }

    /**
     * Generates the string the inform the user of how much time remains.
     */
    protected static void printTimeLeft(int[] timeArray) {
        String output = "Time remaining: " + UiLogic.padOutput(timeArray);
        printToUser(output);
    }
}
