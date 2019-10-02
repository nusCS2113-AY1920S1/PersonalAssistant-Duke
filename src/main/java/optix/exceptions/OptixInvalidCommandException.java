package optix.exceptions;

public class OptixInvalidCommandException extends OptixException {
    public OptixInvalidCommandException() {
        super("☹ OOPS!!! That is an invalid command\n"
                + "Please try again. \n");
    }
}
