package duke.exceptions;

public class DukeScheduleException extends DukeException {

    @Override
    public String getMessage() {
        return super.getMessage() + "This task clashes with existing tasks!";
    }
}
