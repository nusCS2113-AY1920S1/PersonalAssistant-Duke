package oof.logic.command.organization.exceptions;

import oof.commons.exceptions.command.CommandException;

public class EmptyListException extends CommandException {

    public EmptyListException(String message) {
        super(message);
    }
}
