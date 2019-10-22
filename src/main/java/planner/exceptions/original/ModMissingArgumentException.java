package planner.exceptions.original;

public class ModMissingArgumentException extends ModException {

    public ModMissingArgumentException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "Missing " + this.getMessage();
    }
}
