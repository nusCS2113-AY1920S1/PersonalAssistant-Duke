package sgtravel.logic.commands.results;

/**
 * Abstract class which represents the result of a command's execution as a string.
 */
public abstract class CommandResult {
    protected String message;

    /**
     * Gets the message in the object.
     *
     * @return message The message in the object.
     */
    public String getMessage() {
        return message;
    }
}
