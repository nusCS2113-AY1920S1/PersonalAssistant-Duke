package sgtravel.logic.commands.results;

/**
 * Defines the command result of an exit command as a string.
 */
public class CommandResultExit extends CommandResult {
    private String message;

    /**
     * Constructs a basic CommandResultImage object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultExit(String message) {
        this.message = message;
    }

    /**
     * Gets the message.
     *
     * @return message The message in this object.
     */
    @Override
    public String getMessage() {
        return message;
    }
}
