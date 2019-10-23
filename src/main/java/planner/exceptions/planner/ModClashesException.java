package planner.exceptions.planner;

import planner.exceptions.original.ModException;

public class ModClashesException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "This module has already been added!";
    }
}
