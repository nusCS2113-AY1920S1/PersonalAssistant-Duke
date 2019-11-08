package CustomExceptions;

import Enums.ExceptionType;

public class DuplicateException extends Exception {
    private static final String LINE = "___________________________________________________________________________________\n";
    public static final String DUPLICATE_TASK = "\tDuplicate task detected ";

    private String message;

    public DuplicateException(int index) {
        message = DUPLICATE_TASK + "Task: " + (index+1) + "\n";
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
