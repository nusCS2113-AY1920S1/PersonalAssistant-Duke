package com.nwjbrandon.duke.exceptions;

import com.nwjbrandon.duke.services.ui.Terminal;

public class DukeWrongCommandException extends DukeException {

    /**
     * Error message for wrong command.
     */
    public DukeWrongCommandException() {
    }

    /**
     * Show the error message for wrong command.
     */
    @Override
    public void showError() {
        String errorMessage = "☹ OOPS!!! I'm sorry, but I don't know what that means :-(";
        Terminal.showError(errorMessage);
    }
}
