package optix.exceptions;

/**
 * Thrown when the format of the date given is wrong
 * or when the date does not exist.
 */
public class OptixInvalidDateException extends OptixException {
    public OptixInvalidDateException() {
        super("☹ OOPS!!! That is an invalid date.\n"
                + "Please try again. \n");
    }
}