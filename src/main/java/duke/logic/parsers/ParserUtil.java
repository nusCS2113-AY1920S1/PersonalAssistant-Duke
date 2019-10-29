package duke.logic.parsers;

import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.DukeEmptyFieldException;
import duke.commons.exceptions.DukeException;
import duke.commons.Messages;
import duke.commons.exceptions.InputNotIntException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.commons.exceptions.UnknownConstraintException;
import duke.logic.commands.RouteGenerateCommand;

/**
 * Defines parsing methods for utility functions.
 */
public class ParserUtil {


    /**
     * Creates a new RouteGenerateCommand from input, factoring for invalid or empty fields.
     *
     * @param input The userInput read by the user interface.
     * @return The RouteGenerateCommand.
     * @throws DukeEmptyFieldException If there is an empty field.
     * @throws UnknownConstraintException If the constraint is unknown.
     */
    public static RouteGenerateCommand createRouteGenerateCommand(String input) throws DukeEmptyFieldException,
            UnknownConstraintException {
        String[] details = input.split(" to | by ", 3);
        if (details.length == 3) {
            try {
                return new RouteGenerateCommand(details[0], details[1], Constraint.valueOf(details[2].toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new UnknownConstraintException();
            }
        }

        throw new DukeEmptyFieldException(Messages.ERROR_FIELDS_EMPTY);
    }

    /**
     * Gets the field at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The field.
     */
    public static String getFieldInList(int index, int listSize, String userInput) throws DukeException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return fields[index].strip();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException(String.valueOf(index));
        }

        throw new DukeException(Messages.ERROR_INDEX_OUT_OF_BOUNDS);
    }

    /**
     * Gets the integer at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The integer.
     */
    public static int getIntegerInList(int index, int listSize, String userInput) throws InputNotIntException,
            QueryOutOfBoundsException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return Integer.parseInt(fields[index].strip());
            }
        } catch (NumberFormatException e) {
            throw new InputNotIntException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException(String.valueOf(index));
        }

        throw new QueryOutOfBoundsException("INTEGER");
    }

    /**
     * Gets the integer index at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The integer.
     */
    public static int getIntegerIndexInList(int index, int listSize, String userInput) throws InputNotIntException,
            QueryOutOfBoundsException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return Integer.parseInt(fields[index].strip()) - 1;
            }
        } catch (NumberFormatException e) {
            throw new InputNotIntException();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new QueryOutOfBoundsException(String.valueOf(index));
        }

        throw new QueryOutOfBoundsException("INTEGER");
    }
}
