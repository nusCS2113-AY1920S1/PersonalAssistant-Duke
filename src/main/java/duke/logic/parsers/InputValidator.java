package duke.logic.parsers;

import duke.commons.exceptions.DukeException;

public class InputValidator {

    public static void validate(String userInput) throws DukeException {
        if (userInput.trim().length() == 0) {
            throw new DukeException("OOPS!!! The description of the command cannot be empty.");
        }
        //TODO: Throw exceptions for different commands
    }
}
