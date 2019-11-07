package oof.logic.command.semester.exceptions;

import oof.commons.exceptions.command.CommandException;

public class SemesterNotSelectedException extends CommandException {

    public SemesterNotSelectedException(String message) {
        super(message);
    }
}
