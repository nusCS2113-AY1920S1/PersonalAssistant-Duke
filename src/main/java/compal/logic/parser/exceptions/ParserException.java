package compal.logic.parser.exceptions;

import compal.commons.LogUtils;
import compal.ui.UiUtil;

import java.util.logging.Logger;


/**
 * This static inner class is the custom exception class extending Exception
 * that overwrites toString() for returning custom exception messages.
 * It is thrown when command is unknown or when there are invalid arguments.
 */
//@@author SholihinK
public class ParserException extends Exception {

    private String description;
    private UiUtil uiUtil;
    private static final Logger logger = LogUtils.getLogger(ParserException.class);

    public ParserException(String description) {
        this.description = description;
        uiUtil = new UiUtil();
    }

    @Override
    public String toString() {
        logger.warning("Parser exception detected :" + description);
        uiUtil.printg(description);
        compal.ui.UiUtil.tabWindow.getSelectionModel().select(0);
        return description;
    }

    @Override
    public String getMessage() {
        return description;
    }
}
