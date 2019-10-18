package duke.commands.results;

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
