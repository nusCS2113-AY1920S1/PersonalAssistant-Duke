package oof.exception.command;

public class TaskAlreadyCompletedException extends CommandException {

    public TaskAlreadyCompletedException(String message) {
        super(message);
    }
}
