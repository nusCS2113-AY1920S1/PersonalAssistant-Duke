package exception;

public class CommandInvalidException extends DukeException {
    private String command;

    public CommandInvalidException(String falseCommand) {
        super("     ☹ OOPS: I don't understand what you have entered: ");
        this.command = falseCommand;
    }

    @Override
    public String showError() {
        return this.getMessage() + command + "\n     Please check help for more information on what command you can use.";
    }
}
