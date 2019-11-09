package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;

import java.time.LocalDate;

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
            throw new ProgramException("The food cost must be specified and must be numeric.");
        }
        if (!amountInput.matches(positiveValidator)) {
            throw new ProgramException("Only positive value is accepted.");
        }
    }

    /**
     * validate the date so that transaction that happens in the future will not be recorded.
     * @param localDate the date of transaction.
     * @throws ProgramException if <code>LocalDate</code> is after <code>LocalDate.now()</code>.
     */
    public static void validateDate(LocalDate localDate) throws ProgramException {
        if (localDate.isAfter(LocalDate.now())) {
            throw new ProgramException("Cannot add transaction that happens in the future.");
        }
    }


}
