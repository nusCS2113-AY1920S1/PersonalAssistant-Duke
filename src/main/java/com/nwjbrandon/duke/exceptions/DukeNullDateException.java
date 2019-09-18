package com.nwjbrandon.duke.exceptions;

import com.nwjbrandon.duke.services.ui.Terminal;

public class DukeTaskCollisionException extends DukeException {

    /**
     * Show the error message for empty command.
     */
    @Override
    public void showError() {
        String errorMessage = "☹ OOPS!!! Removing task because there is a task on that day.";
        Terminal.showError(errorMessage);
    }

}
