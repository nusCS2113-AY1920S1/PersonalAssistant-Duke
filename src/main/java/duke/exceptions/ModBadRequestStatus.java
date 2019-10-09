package duke.exceptions;

public class ModBadRequestStatus extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Bad Status Connection!";
    }

}
