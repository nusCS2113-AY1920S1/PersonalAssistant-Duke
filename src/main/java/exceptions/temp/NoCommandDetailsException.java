package exceptions.temp;

import exceptions.DukeException;

public class NoCommandDetailsException extends DukeException {
    public NoCommandDetailsException() {
        super("Please enter the command details!");
    }
}
