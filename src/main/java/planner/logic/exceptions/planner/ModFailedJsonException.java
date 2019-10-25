package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModFailedJsonException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Failed to parse data file!";
    }
}
