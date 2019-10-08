package exception;

public class CommandEmptyException extends DukeException {
    public CommandEmptyException() {
        super("☹ OOPS: Command cannot be empty, please input a command.");
    }
}