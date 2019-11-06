package sgtravel.logic.parsers;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ParseException;

import java.util.logging.Logger;

/**
 * Defines parsing methods for utility functions.
 */
public class ParserUtil {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Gets the field at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The field.
     */
    public static String getFieldInList(int index, int listSize, String userInput) throws ParseException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return fields[index].strip();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }

        throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
    }

    /**
     * Gets the integer at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The integer.
     * @throws ParseException If the String cannot be parsed.
     */
    public static int getIntegerInList(int index, int listSize, String userInput) throws ParseException {
        try {
            String[] fields = userInput.split(" ", listSize);
            if (index >= 0 && index < listSize) {
                return Integer.parseInt(fields[index].strip());
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
        }

        throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
    }

    /**
     * Gets the integer index at index in a String list delimited by whitespace.
     *
     * @param index The index of field.
     * @param listSize The total size of String list.
     * @param userInput The userInput read by the user interface.
     * @return The integer index.
     * @throws ParseException If the String cannot be parsed.
     */
    public static int getIntegerIndexInList(int index, int listSize, String userInput) throws ParseException {
        return getIntegerInList(index, listSize, userInput) - 1;
    }
}
