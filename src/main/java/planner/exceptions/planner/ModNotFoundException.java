package planner.exceptions.planner;

import planner.exceptions.original.ModException;

public class ModNotFoundException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Module not found :(";
    }
}
