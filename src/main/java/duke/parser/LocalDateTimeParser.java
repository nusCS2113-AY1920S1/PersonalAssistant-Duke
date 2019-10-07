package duke.parser;

import duke.exception.DukeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeParser {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    /**
     * Converts a LocalDateTime to a user readable string.
     *
     * @param localDateTime LocalDateTime object that we wish to convert
     * @return String that is a formatted date and time
     */
    public static String toString(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * Converts a {@code String} to a {@code LocalDateTime}.
     *
     * @param string {@code String} to convert.
     * @return {@code LocalDateTime} corresponding to the string.
     */
    public static LocalDateTime fromString(String string) throws DukeException {
        try {
            return LocalDateTime.parse(string, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date time input"); // todo: update DukeException
        }
    }
}
