package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModNotFoundException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Module not found :(";
    }
}
