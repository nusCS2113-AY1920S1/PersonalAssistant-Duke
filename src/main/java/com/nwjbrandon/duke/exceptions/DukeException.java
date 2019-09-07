package com.nwjbrandon.duke.exceptions;

import com.nwjbrandon.duke.services.ui.Terminal;

public class DukeException extends Exception {

    /**
     * Error message for general command.
     */
    private String errorMessage;

    DukeException() {
    }

    /**
     * Create error exception for general command.
     * @param errorMessage name of command.
     */
    public DukeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Show the error message for general command.
     */
    public void showError() {
        Terminal.showError(errorMessage);
    }

}
