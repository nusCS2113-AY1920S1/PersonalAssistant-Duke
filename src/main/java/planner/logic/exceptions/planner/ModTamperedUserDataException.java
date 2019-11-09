package planner.logic.exceptions.planner;

import planner.logic.exceptions.legacy.ModException;

public class ModTamperedUserDataException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "User data has been tampered, not using saved data!";
    }
}
