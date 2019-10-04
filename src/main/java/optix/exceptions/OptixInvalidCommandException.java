package optix.exceptions;

/**
 * Thrown when user does not provide the correct input.
 */
public class OptixInvalidCommandException extends OptixException {
    public OptixInvalidCommandException() {
        super("☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n");
    }
}
