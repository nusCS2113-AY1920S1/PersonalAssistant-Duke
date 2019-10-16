package exception;

public class CommandEmptyException extends WordUpException {
    public CommandEmptyException() {
        super(" OOPS: Command cannot be empty, please input a command.");
    }
}