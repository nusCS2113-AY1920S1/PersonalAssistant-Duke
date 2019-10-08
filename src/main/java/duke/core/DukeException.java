package duke.core;

public class DukeException extends Exception {
    public DukeException(String message) {
        super("Oops, I wasn't able to understand that. " + message);
    }
}