package compal.logic.command.exceptions;

import compal.ui.UiUtil;

/**
 * This static inner class is the custom exception class extending Exception
 * that overwrites toString() for returning custom exception messages.
 * It is thrown when command is unknown or when there are invalid arguments.
 */

public class CommandException extends Exception {

    private String description;
    private UiUtil uiUtil;

    public CommandException(String description) {
        this.description = description;
        uiUtil = new UiUtil();
    }

    @Override
    public String toString() {
        uiUtil.printg(description);
        return description;
    }
}
