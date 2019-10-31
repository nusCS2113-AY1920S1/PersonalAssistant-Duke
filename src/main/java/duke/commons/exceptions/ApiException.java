package duke.commons.exceptions;

public class ApiException extends DukeException {

    public ApiException() {
        super("API failed.");
    }
}
