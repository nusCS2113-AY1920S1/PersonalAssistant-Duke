package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModNoPrerequisiteException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "This module has no prerequisites to predict a CAP for!";
    }
}


