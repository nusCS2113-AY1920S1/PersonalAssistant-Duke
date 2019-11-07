package oof.logic.command.productivity.exceptions;

import oof.commons.exceptions.command.CommandException;

public class TrackerNotFoundException extends CommandException {

    public TrackerNotFoundException(String message) {
        super(message);
    }
}
