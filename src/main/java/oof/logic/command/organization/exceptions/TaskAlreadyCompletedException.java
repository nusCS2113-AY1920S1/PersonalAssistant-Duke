package oof.logic.command.organization.exceptions;

import oof.commons.exceptions.command.CommandException;

public class TaskAlreadyCompletedException extends CommandException {

    public TaskAlreadyCompletedException(String message) {
        super(message);
    }
}
