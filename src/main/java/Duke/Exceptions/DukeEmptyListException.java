package Duke.Exceptions;

public class DukeEmptyListException extends DukeException {

    @Override
    public String getMessage() {
        return super.getMessage() + "There are no tasks in the list!";
    }
}
