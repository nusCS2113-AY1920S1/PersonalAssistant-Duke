package compal.logic.command.exceptions;

import compal.commons.LogUtils;
import compal.ui.UiUtil;

import java.util.logging.Logger;


//@@author SholihinK

/**
 * This static inner class is the custom exception class extending Exception
 * that overwrites toString() for returning custom exception messages.
 * It is thrown when command is unknown or when there are invalid arguments.
 */
public class CommandException extends Exception {

    private String description;
    private UiUtil uiUtil;
    private static final Logger logger = LogUtils.getLogger(CommandException.class);

    public CommandException(String description) {
        this.description = description;
        uiUtil = new UiUtil();
    }

    @Override
    public String toString() {
        logger.warning("Command exception detected :" + description);
        uiUtil.printg(description);
        return description;
    }

    @Override
    public String getMessage() {
        return description;
    }
}
