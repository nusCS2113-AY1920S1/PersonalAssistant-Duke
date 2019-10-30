package dolla.exception;

/**
 * This Exception class is used to handle limit related exceptions to give the user a better understanding of why
 * the program crashed.
 */
 //@author Weng-Kexin
public class LimitException extends Exception {

    private static final String MSG_INVALID_COMMAND_FORMAT = "\tOOPS! Please follow the format 'set [limitType] [AMOUNT] [DURATION]'";
    private static final String MSG_INVALID_LIMIT_TYPE = "\tOOPS! Please specify the type of limit you want to set!";

    public LimitException(String message) {
        super(message);
    }

    public static String invalidLimitFormat() {
        return MSG_INVALID_COMMAND_FORMAT;
    }

    public static String invalidLimitType() {
        return MSG_INVALID_LIMIT_TYPE;
    }
}
