package duke.exceptions;

public class ModEmptyCommandException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "Command cannot be empty!";
    }
}
