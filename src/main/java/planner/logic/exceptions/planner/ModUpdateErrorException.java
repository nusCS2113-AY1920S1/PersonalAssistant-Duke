package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModUpdateErrorException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "You can only update modules using \"module\" !";
    }
}
