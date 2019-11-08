package oof.logic.command.productivity.exceptions;

import oof.commons.exceptions.command.CommandException;

public class ScheduleEmptyException extends CommandException {
    public ScheduleEmptyException(String message) {
        super(message);
    }
}
