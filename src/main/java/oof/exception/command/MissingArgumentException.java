package oof.exception.command;


public class MissingArgumentException extends CommandException {

    public MissingArgumentException(String message) {
        super(message);
    }
}
