package com.nwjbrandon.duke.exceptions;

import com.nwjbrandon.duke.services.ui.Terminal;

public class DukeNullDateException extends DukeException {

    /**
     * Show the error message for empty command.
     */
    @Override
    public void showError() {
        String errorMessage = "☹ OOPS!!! I'm sorry, but there is no date to be snoozed";
        Terminal.showError(errorMessage);
    }

}
