package com.nwjbrandon.duke.exceptions;

import com.nwjbrandon.duke.services.ui.Terminal;

public class DukeWrongCommandFormatException extends DukeException {

    /**
     * Error message for empty command.
     */
    private String errorMessage = "☹ OOPS!!! The description of a %s is in the wrong format.";

    /**
     * Create error exception for empty command.
     * @param taskName name of command.
     */
    public DukeWrongCommandFormatException(String taskName) {
        errorMessage = String.format(errorMessage, taskName);
    }

    /**
     * Show the error message for empty command.
     */
    @Override
    public void showError() {
        Terminal.showError(errorMessage);
    }

}
