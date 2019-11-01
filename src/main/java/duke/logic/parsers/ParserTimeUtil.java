package duke.logic.parsers;

import duke.commons.Messages;
import duke.commons.enumerations.TimePatternType;
import duke.commons.exceptions.ParseException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;

/**
 * Parser for Time related operations.
 */
public class ParserTimeUtil {
    /**
     * Parses a String into a LocalDateTime object.
     *
     * @param line The input String for Date extraction.
     * @return The date and time, missing information wil be padded with current time information.
     * @throws ParseException If the format is invalid.
     */
    public static LocalDateTime parseStringToDate(String line) throws ParseException {
        String[] patterns = {"['next ']['this ']E", "['this ']['next ']EEEE", "dd/MM/yyyy HHmm",
                             "dd/MM/yy HHmm", "HHmm", "dd/MM/yyyy",
                             "dd/MM/yy", "yyyy-MM-dd'T'HH:mm[:ss.n]"};
        TimePatternType[] types = {TimePatternType.DAY_OF_WEEK, TimePatternType.DAY_OF_WEEK,
            TimePatternType.DATE_TIME, TimePatternType.DATE_TIME, TimePatternType.TIME, TimePatternType.DATE,
                TimePatternType.DATE, TimePatternType.DATE_TIME};
        for (int i = 0; i < patterns.length;) {
            try {
                TemporalAccessor accessor = DateTimeFormatter.ofPattern(patterns[i]).parse(line);
                switch (types[i]) {
                case DAY_OF_WEEK:
                    LocalDateTime localDateTime = LocalDateTime.now();
                    return localDateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.from(accessor)));
                case DATE_TIME:
                    return LocalDateTime.from(accessor);
                case TIME:
                    LocalDate localDate = LocalDate.now();
                    return localDate.atTime(LocalTime.from(accessor));
                case DATE:
                    LocalTime localTime = LocalTime.now();
                    return localTime.atDate(LocalDate.from(accessor));
                default:
                }
            } catch (DateTimeParseException e) {
                ++i;
            }
        }
        throw new ParseException(Messages.ERROR_INPUT_INVALID_FORMAT);
    }
}
