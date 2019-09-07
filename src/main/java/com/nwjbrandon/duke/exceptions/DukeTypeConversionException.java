package com.nwjbrandon.duke.exceptions;

import com.nwjbrandon.duke.services.ui.Terminal;

public class DukeTypeConversionException extends DukeException {

    /**
     * Error message for type conversion command.
     */
    public DukeTypeConversionException() {
    }

    /**
     * Show the error message for type conversion command.
     */
    @Override
    public void showError() {
        String errorMessage = "☹ OOPS!!! Cannot convert types.";
        Terminal.showError(errorMessage);
    }

}
