package duke.logic.commands.results;

/**
 * Class representing the result of an exit command as a string.
 */
public class CommandResultExit extends CommandResult {
    private String message;

    public CommandResultExit(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
