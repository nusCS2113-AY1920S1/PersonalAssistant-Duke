package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModClashesException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "This module has already been added!";
    }
}
