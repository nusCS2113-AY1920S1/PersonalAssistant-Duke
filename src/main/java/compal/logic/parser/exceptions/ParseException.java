package compal.logic.parser.exceptions;

import compal.ui.UiUtil;

/**
 * This static inner class is the custom exception class extending Exception
 * that overwrites toString() for returning custom exception messages.
 * It is thrown when command is unknown or when there are invalid arguments.
 */

public class ParseException extends Exception {

    String description;
    private UiUtil uiUtil;

    public ParseException(String description) {
        this.description = description;
        uiUtil = new UiUtil();
    }

    @Override
    public String toString() {
        uiUtil.printg("Parsing Exception:\n");
        uiUtil.printg(description);
        return description;
    }
}
