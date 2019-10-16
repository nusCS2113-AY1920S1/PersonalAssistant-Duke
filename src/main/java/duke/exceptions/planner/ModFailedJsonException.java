package duke.exceptions.planner;

import duke.exceptions.ModException;

public class ModFailedJsonException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Failed to parse data file!";
    }
}
