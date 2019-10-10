package duke.exceptions;

public class ModEmptyListException extends ModException {

    @Override
    public String getMessage() {
        return super.getMessage() + "There are no tasks in the list!";
    }
}
