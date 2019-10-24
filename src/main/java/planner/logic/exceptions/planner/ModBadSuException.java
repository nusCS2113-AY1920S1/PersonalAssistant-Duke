package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModBadSuException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "S/U option is not allowed for this module!";
    }
}
