package oof.logic.command.module.exceptions;

import oof.commons.exceptions.command.CommandException;

public class ModuleNotSelectedException extends CommandException {

    public ModuleNotSelectedException(String message) {
        super(message);
    }
}
