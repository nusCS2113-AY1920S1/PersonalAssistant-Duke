package planner.util;

import planner.exceptions.ModInvalidTimeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
     * @throws ModInvalidTimeException if user inputs a date format that is not supported by Duke.
     */
    public static LocalDateTime getStringToDate(String dateAndTime) throws ModInvalidTimeException {
        boolean am = dateAndTime.contains("am");
        boolean pm = dateAndTime.contains("pm");
        if (am && pm) {
            throw new ModInvalidTimeException();
        }
        dateAndTime = dateAndTime.trim()
                .replaceAll(" */ *", "/")
                .replaceAll(" *: *", ":")
                .replaceAll(" *- *", "-")
                .replaceAll(" *am *| *pm *", " ")
                .trim();
        List<String> dateFormatStrings = Arrays.asList(
                "dd-MM-yyyy", "dd/MM/yyyy", "dd-MMM-yyyy", "dd/MMM/yyyy", "d/MM/yyyy", "d-MM-yyyy",
                "dd MMM yyyy", "d MMM yyyy", "dd/M/yyyy", "dd-M-yyyy", "d-M-y", "d/M/y", "d/M", "d-M",
                "M/d", "M-d", "M/y", "M-y");
        List<String> hourFormatStrings = Arrays.asList("HH:mm", "HH:mm:ss", "H", "HH", "H:mm", "H:m", "HH:m");
        LocalDateTime localDateTime = getLocalDateTime(dateFormatStrings, hourFormatStrings, dateAndTime);
        if (localDateTime == null) {
            localDateTime = getLocalDateTimeHourOnly(hourFormatStrings, dateAndTime);
        }
        if (localDateTime == null) {
            throw new ModInvalidTimeException();
        }
        if (am || pm) {
            if (localDateTime.getHour() > 12) {
                throw new ModInvalidTimeException();
            }
            if (pm) {
                localDateTime = localDateTime.plusHours(12);
            }
        }
        return localDateTime;
    }

    private static LocalDateTime getLocalDateTimeHourOnly(List<String> hourFormatStrings, String dateAndTime) {
        LocalDateTime localDateTime = null;
        LocalDate currentDate = LocalDate.now();
        for (int j = 0; j < hourFormatStrings.size(); ++j) {
            try {
                if (localDateTime != null) {
                    break;
                }
                DateTimeFormatter fmt = getFormatter("", hourFormatStrings.get(j));
                localDateTime = LocalDateTime.of(currentDate, LocalTime.parse(" " + dateAndTime, fmt));
            } catch (DateTimeParseException e) {
                continue;
            }
        }
        return localDateTime;
    }

    private static LocalDateTime getLocalDateTime(
            List<String> dateFormatStrings,
            List<String> hourFormatStrings,
            String dateAndTime) {
        LocalDateTime localDateTime = null;
        for (int i = 0; i < dateFormatStrings.size(); ++i) {
            for (int j = 0; j < hourFormatStrings.size(); ++j) {
                try {
                    if (localDateTime != null) {
                        break;
                    }
                    DateTimeFormatter fmt = getFormatter(dateFormatStrings.get(i), hourFormatStrings.get(j));
                    localDateTime = LocalDateTime.parse(dateAndTime, fmt);
                } catch (DateTimeParseException e) {
                    continue;
                }
            }
        }
        return localDateTime;
    }

    private static DateTimeFormatter getFormatter(String dateFormat, String hourFormat) {
        return new DateTimeFormatterBuilder()
                .appendPattern(dateFormat)
                .optionalStart().appendPattern(" " + hourFormat).optionalEnd()
                .optionalStart().appendPattern(" HHmm").optionalEnd()
                .optionalStart().appendPattern(" hmm").optionalEnd()
                .optionalStart().appendPattern(" hm").optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
    }
}