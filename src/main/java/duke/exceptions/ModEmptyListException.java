package duke.exceptions;

public class ModEmptyListException extends ModException {

    private String type;

    public ModEmptyListException(String type) {
        this.type = type;
    }

    public ModEmptyListException() {
        this("task");
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "There are no " + this.type + " in the list!";
    }
}
