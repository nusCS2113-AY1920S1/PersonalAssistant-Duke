package duke.exceptions.planner;

import duke.exceptions.ModException;

public class ModBadRequestStatus extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Bad Status Connection!";
    }

}
