package seedu.hustler.ui.timer;

import seedu.hustler.ui.timer.threadErrorUIMessages.threadErrorType;
import seedu.hustler.ui.timer.timer.threadStatus;

/**
 * Manages all code that will need to print information for the user.
 * Acts as a node and HQ for all such user-interface activities.
 */
public class timerUI {
    /**
     * All strings to be printed will always be generated else. This private
     * function will be the last part of every printing-related; this method
     * is the gate of sorts. This deliberate abstraction always the developer
     * to mute printing if required.
     */
    private static void printToUser(String output) {
        System.out.println(output);
    }

    /**
     * The main location to generate messages related to why a timer starts to
     * countdown (new timer started, old timer resumed, etc).
     */
    protected static void printThreadStart(threadStatus threadstatus, int[] timeArray) {
        if (threadstatus == threadStatus.RUNNING) {
            printToUser(threadStartUIMessages.TimerStart(timeArray));
        } else if (threadstatus == threadStatus.RESUMED) {
            printToUser(threadStartUIMessages.TimerResumed());
        }
    }

    /**
     * The main location to generate messages related to why a timer has
     * stopped its countdown (has it been paused, stopped prematurely, etc).
     */
    protected static void printThreadInterrupt(threadStatus threadstatus) {
        if (threadstatus == threadStatus.PAUSED) {
            printToUser(threadStopUIMessages.TimerPaused());
        } else if (threadstatus == threadStatus.RESET) {
            printToUser(threadStopUIMessages.TimerStopped());
        } else if (threadstatus == threadStatus.RUNNING || threadstatus == threadStatus.RESUMED) {
            printToUser(threadStopUIMessages.TimesUp());
            timer.threadstatus = threadStatus.FINISHED;
        }
    }

    /**
     * The main location to generate messages related to all errors that arise
     * to the the timer/ thread (invalid commands, etc). 
     */
    protected static void printThreadError(threadErrorType threaderrortype) {
        if (threaderrortype == threadErrorType.RESUMEERROR) {
            printToUser(threadErrorUIMessages.NoTimerToResumeError());
        } else if (threaderrortype == threadErrorType.PAUSEERROR) {
            printToUser(threadErrorUIMessages.NoTimerToPauseError());
        } else if (threaderrortype == threadErrorType.STOPERROR) {
            printToUser(threadErrorUIMessages.NoTimerToStopError());
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
