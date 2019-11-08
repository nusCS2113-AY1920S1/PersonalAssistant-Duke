package CustomExceptions;

public class TimeClashException extends Exception {
    private static final String LINE = "___________________________________________________________________________________\n";
    private static final String TIME_CLASH_TEXT = "\tTime Clash Detected ";

    private String message;

    public TimeClashException(int index) {
        message = TIME_CLASH_TEXT + "Task: " + (index+1) + "\n";
    }

    /**
     * toString() method returning the message of the Exception
     * @return the message of the Exception
     */
    @Override
    public String toString(){
        return LINE + message + LINE;
    }
}
