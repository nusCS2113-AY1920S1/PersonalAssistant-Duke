package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModBadRequestStatus extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Bad Status Connection!";
    }

}
