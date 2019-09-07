package com.nwjbrandon.duke.exceptions;

import com.nwjbrandon.duke.services.ui.Terminal;

public class DukeOutOfBoundException extends DukeException {

    /**
     * Error message for out of bound command.
     */
    public DukeOutOfBoundException() {
    }

    /**
     * Show the error message for out of bound command.
     */
    @Override
    public void showError() {
        String errorMessage = "☹ OOPS!!! I'm cannot find the task.";
        Terminal.showError(errorMessage);
    }

}
