package duke.logic.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

import duke.exception.DukeException;

/**
 * Class that handles the formatting and parsing of the various date time formats
 */
public class DateTimeParser {

    /**d
     * Method that parses the date time based on today, tomorrow with time or an actual date
     * @param info the string of the date
     * @return return the LocalDateTime of the inputted date
     * @throws DukeException throws an exception if wrong format inputted
     */
    public static LocalDateTime parseDateTime(String info) throws DukeException {
        LocalDateTime result;
        switch (info) {
        case "today":
            result = LocalDateTime.now();
            break;
        case "tomorrow":
            result = LocalDateTime.now().plusDays(1);
            break;
        default:
            String[] details = info.split(" ", 2);
            switch (details[0]) {
            case "today":
                try {
                    result = LocalDateTime.of(LocalDate.now(),
                            LocalTime.parse(details[1], DateTimeFormatter.ofPattern("HHmm")));
                } catch (DateTimeParseException e) {
                    throw new DukeException("Please enter a time behind today in HHmm format!");
                }
                break;
            case "tomorrow":
                try {
                    result = LocalDateTime.of(LocalDate.now().plusDays(1),
                            LocalTime.parse(details[1], DateTimeFormatter.ofPattern("HHmm")));
                } catch (DateTimeParseException e) {
                    throw new DukeException("Please enter a time behind tomorrow in HHmm format!");
                }
                break;
            default:
                if (details.length == 1) {
                    try {
                        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                                .appendPattern("ddMMyy[HH:mm:ss]")
                                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                                .toFormatter();
                        result = LocalDateTime.parse(info, formatter);
                    } catch (DateTimeParseException e) {
                        throw new DukeException("Please enter a date in ddMMyy format!");
                    }
                } else {
                    try {
                        result = LocalDateTime.parse(info, DateTimeFormatter.ofPattern("ddMMyy HHmm"));
                    } catch (DateTimeParseException e) {
                        throw new DukeException("Please enter a date in ddMMyy HHmm format!");
                    }
                }
            }
        }
        return result;
    }
}
