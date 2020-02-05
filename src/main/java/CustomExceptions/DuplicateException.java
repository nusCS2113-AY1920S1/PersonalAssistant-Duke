package CustomExceptions;

public class DuplicateException extends Exception {
    private static final String LINE = "___________________________________________________________________________________\n";
    private static final String DUPLICATE_TASK = "\tDuplicate task detected ";
    private static final String RED = "\u001b[31m";

    private String message;

    /**
     * Adds the appropriate index to the Duplicate task message.
     * @param index index of the task with the clash
     */
    public DuplicateException(int index) {
        message = DUPLICATE_TASK + "Task: " + (index + 1) + "\n";
    }

    /**
     * toString() method returning the message of the Exception.
     * @return the message of the Exception
     */
    @Override
    public String toString() {
        return RED + LINE + message + LINE;
    }
}
