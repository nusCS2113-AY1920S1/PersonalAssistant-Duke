package seedu.hustler.ui.timer;

import seedu.hustler.ui.timer.ThreadErrorUiMessages.ThreadErrorType;
import seedu.hustler.ui.timer.Timer.ThreadStatus;

/**
 * Manages all code that will need to print information for the user.
 * Acts as a node and HQ for all such user-interface activities.
 */
public class TimerUI {
    
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
    protected static void printThreadStart(ThreadStatus threadstatus, int[] timeArray) {
        if (threadstatus == ThreadStatus.RUNNING) {
            printToUser(ThreadStartUiMessages.timerStart(timeArray));
        } else if (threadstatus == ThreadStatus.RESUMED) {
            printToUser(ThreadStartUiMessages.timerResumed());
        }
    }

    /**
     * The main location to generate messages related to why a timer has
     * stopped its countdown (has it been paused, stopped prematurely, etc).
     */
    protected static void printThreadInterrupt(ThreadStatus threadstatus) {
        if (threadstatus == ThreadStatus.PAUSED) {
            printToUser(ThreadStopUiMessages.timerPaused());
        } else if (threadstatus == ThreadStatus.RESET) {
            printToUser(ThreadStopUiMessages.timerStopped());
        } else if (threadstatus == ThreadStatus.RUNNING || threadstatus == ThreadStatus.RESUMED) {
            printToUser(ThreadStopUiMessages.timesUp());
            Timer.threadstatus = ThreadStatus.FINISHED;
        }
    }

    /**
     * The main location to generate messages related to all errors that arise
     * to the the timer/ thread (invalid commands, etc). 
     */
    protected static void printThreadError(ThreadErrorType threaderrortype) {
        if (threaderrortype == ThreadErrorType.RESUMEERROR) {
            printToUser(ThreadErrorUiMessages.noTimerToResumeError());
        } else if (threaderrortype == ThreadErrorType.PAUSEERROR) {
            printToUser(ThreadErrorUiMessages.noTimerToPauseError());
        } else if (threaderrortype == ThreadErrorType.STOPERROR) {
            printToUser(ThreadErrorUiMessages.noTimerToStopError());
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
