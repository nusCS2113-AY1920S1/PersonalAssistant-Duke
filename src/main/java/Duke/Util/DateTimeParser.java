package Duke.Util;

import Duke.Exceptions.DukeInvalidTimeException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;

public class DateTimeParser {

    /**
     * Parser for date with or without time inputs for deadline and event tasks.
     * @param dateAndTime Date and time portion of the user input string.
     * @return LocalDateTime parsed based on the user input format.
     * @throws DukeInvalidTimeException if user inputs a date format that is not supported by Duke.
     */
    public static LocalDateTime getStringToDate(String dateAndTime) throws DukeInvalidTimeException {
        dateAndTime = dateAndTime.trim();
        dateAndTime = dateAndTime.strip();
        List<String> formatStrings = Arrays.asList("dd-MM-yyyy", "dd/MM/yyyy", "dd-MMM-yyyy", "MM/d/yyyy", "d-MM-yyyy",
                "MMM dd yyyy", "dd/MM/yyyy", "dd/MM/yyyy", "dd/MM/yyyy", "MM/dd/yyyy", "MM-dd-yyyy");
        int i = 0;
        LocalDateTime localDateTime = null;
        while (i < formatStrings.size()) {
            try {
                DateTimeFormatter fmt =  new DateTimeFormatterBuilder()
                        .appendPattern(formatStrings.get(i))
                        .optionalStart().appendPattern(" HH:mm").optionalEnd()
                        .optionalStart().appendPattern(" HHmm").optionalEnd()
                        .optionalStart().appendPattern(" hmm").optionalEnd()
                        .optionalStart().appendPattern(" hm").optionalEnd()
                        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter();
                localDateTime = LocalDateTime.parse(dateAndTime, fmt);
                if (localDateTime != null) break;
            } catch (DateTimeParseException e) {
                i++;
            }
        }
        if (localDateTime == null) {
            throw new DukeInvalidTimeException();
        } else {
            return localDateTime;
        }
    }
}