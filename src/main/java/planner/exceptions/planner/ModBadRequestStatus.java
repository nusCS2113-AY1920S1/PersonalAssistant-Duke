package planner.exceptions.planner;

import planner.exceptions.original.ModException;

public class ModBadRequestStatus extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Bad Status Connection!";
    }

}
