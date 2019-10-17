package duke.exceptions.planner;

import duke.exceptions.ModException;

public class ModNotFoundException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Module not found :(";
    }
}
