package optix.exceptions;

public class OptixInvalidDateException extends OptixException {
    public OptixInvalidDateException() {
        super("☹ OOPS!!! That is an invalid date.\n"
                + "Please try again. \n");
    }
}