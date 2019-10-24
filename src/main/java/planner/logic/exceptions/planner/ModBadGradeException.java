package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModBadGradeException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Please enter a valid letter grade!";
    }
}
