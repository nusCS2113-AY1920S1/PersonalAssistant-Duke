package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModTamperedDataException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Data has been tampered, not using saved data!";
    }
}
