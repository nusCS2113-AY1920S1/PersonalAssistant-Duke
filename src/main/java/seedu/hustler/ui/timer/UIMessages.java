package seedu.hustler.ui.timer;

import seedu.hustler.ui.timer.statusTypes.threadStatus;

/**
 * This class contains all classes that generate UIMessages
 * for printing. These includes the threadStart, threadStop
 * and threadError classes.
 */
public class UIMessages {

    /**
     * A class that returns a descrption of the timer activity each timer
     * the timer starts, resumes, etc.
     */
    public static class threadStart {

        /**
         * This method informs the user each time a new timer is started.
         *
         * @param timeArray the current hours, minutes and seconds of the timer.
         * @return a string telling the user that a new timer has been started,
         * along with the timer's duration.
         */
        public static String TimerStart(int[] timeArray) {
            String output = "Timer has commenced! Time set: " + UiLogic.padOutput(timeArray);
            return output;
        }

        /**
         * This method informs the user each time a paused timer is resumed.
         * @return  a string telling the user that a paused timer has been
         * resumed.
         */
        public static String TimerResumed() {
            String output = "Timer mode has resumed!";
            return output;
        }
    }

    /**
     * This class generates the various messages to explain why a timer has
     * been stopped (whether because it has been paused, stopped, etc.).
     * These string messages will then be printed to inform the user of what
     * has happened.
     */
    public static class threadStop {

        /**
         * @return a string telling the user that the timer has been paused.
         */
        public static String TimerPaused() {
            String output = "Timer mode has been paused!";
            return output;
        }

        /**
         * @return a string telling the user that the timer has been stopped.
         */
        public static String TimerStopped() {
            String output = "Timer has been stopped and reset";
            return output;
        }

        /**
         * @return a string telling the user that the countdown is complete..
         */
        public static String TimesUp() {
            String output = "Timer mode has been completed!";
            return output;
        }

    }

    /**
     * This class generates the various error messages to be printed
     * if the user's commands are invalid or if they cannot be performed.
     */
    public static class threadError {

        /**
         * @return a string telling the user that the 'resumetimer' command cannot
         * currently be used.
         */
        public static String NoTimerToResumeError() {
             String output = "Error: No paused timer to resume!";
            return output;
        }

        /**
         * @return a string telling the user that the 'pausetimer' command cannot
         * currently be used.
         */
        public static String NoTimerToPauseError() {
            String output = "Error: No running timer to pause!";
            return output;
        }

        /**
         * @return a string telling the user that the 'stoptimer' command cannot
         * currently be used.
         */
        public static String NoTimerToStopError() {
            String output = "Error! No running timer to stop!";
            return output;
        }
    }
}
