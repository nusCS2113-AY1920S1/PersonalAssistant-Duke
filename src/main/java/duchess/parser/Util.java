package duchess.parser;

import duchess.exceptions.DuchessException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

/**
 * Collection of helpful functions to parse user input.
 */
public class Util {
    private static final String INVALID_FORMAT_MESSAGE = "Please enter dates in the format dd/mm/yyyy hhmm";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
                    .withResolverStyle(ResolverStyle.STRICT);

    private Util() {
        // Note that this class is not meant to be instantiated
        // similar to the Math class.
        //
        // It's simply a collection of utility functions.
    }

    /**
     * Parses a given string and returns a {@code LocalDateTime} object.
     *
     * @param dateTime the string to parse
     * @return {@code LocalDateTime} obtained by parsing the supplied string
     * @throws DuchessException if the supplied string is formatted incorrectly
     */
    public static LocalDateTime parseDateTime(String dateTime) throws DuchessException {
        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new DuchessException(INVALID_FORMAT_MESSAGE);
        }
    }

    /**
     * Parses a list of string and returns a {@code LocalDateTime} object.
     *
     * @param tokens the datetime string split at space
     * @param offset the offset of the datetime tokens from the beginning of the list
     * @return {@code LocalDateTime} object obtained by parsing the supplied tokens
     * @throws DuchessException if the format is invalid or there are insufficient tokens to parse
     */
    public static LocalDateTime parseDateTime(List<String> tokens, int offset) throws DuchessException {
        try {
            String dateTime = tokens.get(offset) + " " + tokens.get(offset + 1);
            return parseDateTime(dateTime);
        } catch (IndexOutOfBoundsException e) {
            throw new DuchessException(INVALID_FORMAT_MESSAGE);
        }
    }

    /**
     * Returns a {@code String} from a {@code LocalDateTime} object with the desired formatting.
     *
     * @param dateTime the object to format
     * @return the formatted string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatter.format(dateTime);
    }
}
