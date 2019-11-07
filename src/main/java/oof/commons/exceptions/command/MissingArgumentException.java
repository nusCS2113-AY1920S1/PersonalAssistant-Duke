package oof.commons.exceptions.command;


public class MissingArgumentException extends CommandException {

    public MissingArgumentException(String message) {
        super(message);
    }
}
