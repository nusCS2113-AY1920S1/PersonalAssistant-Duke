package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;

/**
 * InputValidator is a public class that deals with validating user input.
 */
public class InputValidator {

    private static final String numericValidator = "-?\\d+(\\.\\d+)?";

    /**
     * validate the user input to check whether it's empty.
     * @param userInput String input by user.
     * @throws ProgramException If the userInput is empty.
     */
    public static void validate(String userInput) throws ProgramException {
        if (userInput.trim().length() == 0) {
            throw new ProgramException("OOPS!!! The description of the command cannot be empty.");
        }
    }

    /**
     * validate the amount the user input (nutritional value or food cost) is numeric.
     * @param userInput String input by user.
     * @throws ProgramException If the userInput is not numeric.
     */
    public static void validateAmount(String userInput) throws ProgramException {
        if (!userInput.matches(numericValidator)) {
            throw new ProgramException("The nutritional value or food cost must be numeric.");
        }
    }
}
