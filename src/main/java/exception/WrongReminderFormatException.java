package exception;

public class WrongReminderFormatException extends WordUpException {

    /**
     * Gives user help information regarding multi-line and single line schedule command inputs.
     */
    public WrongReminderFormatException() {
        super(" OOPS: Expected format:\n"
                + "multi-line setup: schedule\n"
                + "single-command: schedule w/word1 word2 ... r/dd-MM-yyyy HHmm");
    }
}
