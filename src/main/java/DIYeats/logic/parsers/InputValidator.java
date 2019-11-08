package DIYeats.logic.parsers;

import DIYeats.commons.exceptions.DukeException;

/**
 * InputValidator is a public class that deals with validating user input.
 */
public class InputValidator {

    /**
     * validate the user input to check whether it's empty.
     * @param userInput String input by user.
     * @throws DukeException If the userInput is empty.
     */
    public static void validate(String userInput) throws DukeException {
        if (userInput.trim().length() == 0) {
            throw new DukeException("OOPS!!! The description of the command cannot be empty.");
        }
        //TODO: Throw exceptions for different commands
    }
}
