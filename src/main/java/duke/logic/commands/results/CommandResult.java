package duke.logic.commands.results;

/**
 * Abstract class which represents the result of a commands execution as a string.
 */
public abstract class CommandResult {
    protected String message;

    public String getMessage() {
        return message;
    }
}
