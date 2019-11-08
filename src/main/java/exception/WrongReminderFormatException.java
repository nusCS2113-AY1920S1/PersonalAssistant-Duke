package exception;

public class WrongReminderFormatException extends WordUpException {
    public WrongReminderFormatException() {
        super(" OOPS: Expected format:\n"
                + "multi-line setup: schedule\n"
                + "single-command: schedule w/word1 word2 ... r/dd-MM-yyyy HHmm");
    }
}
