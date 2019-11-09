package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;

//@@author GaryStu
/**
 * InputValidator is a public class that deals with validating user input.
 */
public class InputValidator {

    private static final String numericValidator = "-?\\d+(\\.\\d+)?";
    private static final String positiveValidator = "^[0-9]\\d*(\\.\\d+)?$";

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
     * @param amountInput amount input by user.
     * @throws ProgramException If the amount input by the user is not numeric or not positive.
     */
    public static void validateAmount(String amountInput) throws ProgramException {
        if (!amountInput.matches(numericValidator)) {
            throw new ProgramException("The nutritional value or food cost must be numeric.");
        }
        if (!amountInput.matches(positiveValidator)) {
            throw new ProgramException("Only positive value is accepted.");
        }
    }
}
