package planner.exceptions.planner;

import planner.exceptions.ModException;

public class ModNotFoundException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Module not found :(";
    }
}
