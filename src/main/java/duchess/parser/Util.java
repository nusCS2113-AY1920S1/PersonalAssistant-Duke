package duchess.parser;

import duchess.exceptions.DuchessException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Collection of helpful functions to parse user input.
 */
public class Util {
    private static final String INVALID_FORMAT_MESSAGE = "Please enter the date and time as such : dd/mm/yyyy hhmm.";
    private static final String INVALID_DATE_FORMAT_MESSAGE = "Please enter the date as such : dd/mm/yyyy.";
    private static final String INVALID_TIME_FORMAT_MESSAGE = "Please enter the time as such : hhmm";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm")
                    .withResolverStyle(ResolverStyle.STRICT);
    private static final String MONDAY = "MONDAY";
    private static final String TUESDAY = "TUESDAY";
    private static final String WEDNESDAY = "WEDNESDAY";
    private static final String THURSDAY = "THURSDAY";
    private static final String FRIDAY = "FRIDAY";
    private static final String SATURDAY = "SATURDAY";
    private static final String SUNDAY = "SUNDAY";

    private Util() {
        // Note that this class is not meant to be instantiated
        // similar to the Math class.
        //
        // It's simply a collection of utility functions.
    }

    /**
     * Obtains an instance of LocalTime from a text string using a formatter of pattern "HHmm".
     *
     * @param time text to parse
     * @return the parsed local time
     */
    private static LocalTime parseTime(String time) throws DuchessException {
        try {
            return LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm"));
        } catch (DateTimeParseException e) {
            throw new DuchessException(INVALID_TIME_FORMAT_MESSAGE);
        }
    }

    /**
     * Obtains an instance of LocalDate from a text string containing a day of week.
     *
     * @param day text to process
     * @return the next nearest date which falls on the day of week indicated by {@code day}
     * @throws DuchessException thrown if text input does not indicate a day of week
     */
    private static LocalDate processDayOfWeek(String day) throws DuchessException {
        String capitalDay = day.toUpperCase();
        switch (capitalDay) {
        case MONDAY:
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        case TUESDAY:
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        case WEDNESDAY:
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        case THURSDAY:
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
        case FRIDAY:
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        case SATURDAY:
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        case SUNDAY:
            return LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        default:
            throw new DuchessException(INVALID_DATE_FORMAT_MESSAGE);
        }
    }

    /**
     * Obtains an instance of LocalDate from a text string using a formatter of pattern "dd/MM/yyyy".
     *
     * @param date date to parse
     * @return the parsed local date
     * @throws DuchessException thrown if text input is not in ISO format, or does not indicate a day of week
     */
    public static LocalDate parseDate(String date) throws DuchessException {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            return processDayOfWeek(date);
        }
    }

    /**
     * Obtains an instance of LocalDateTime from a text input.
     * Text input is parsed to given LocalDate and LocalTime.
     * LocalDate and LocalTime is then used to form LocalDateTime.
     *
     * @param dateTime dateTime to parse
     * @return the parsed local date time
     * @throws DuchessException thrown if invalid
     */
    public static LocalDateTime parseDateTime(String dateTime) throws DuchessException {
        try {
            String[] arr = dateTime.split(" ");
            if (arr.length > 2) {
                throw new DuchessException(INVALID_FORMAT_MESSAGE);
            }
            return LocalDateTime.of(parseDate(arr[0]), parseTime(arr[1]));
        } catch (Exception e) {
            throw new DuchessException(INVALID_FORMAT_MESSAGE);
        }
    }

    /**
     * Parses a date and returns a {@code List<LocalDate>} object.
     *
     * @param date a date input
     * @return the nearest or same monday date and nearest or same sunday date
     */
    public static List<LocalDate> parseToWeekDates(LocalDate date) {
        LocalDate startOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return Arrays.asList(startOfWeek, endOfWeek);
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

    /**
     * Returns a map mapping the parameter to its corresponding values from user input.
     *
     * @param input the raw input from the user
     * @return the mapping of parameter to values
     */
    public static TreeMap<String, String> parameterize(String input) {
        TreeMap<String, String> mappedTokens = new TreeMap<>();

        String currentParameter = "general";
        List<String> collectedTokens = new ArrayList<>();

        for (String token : List.of(input.split("\\s+"))) {
            if (!mappedTokens.containsKey("command")) {
                mappedTokens.put("command", token);
            } else if (token.charAt(0) == '/') {
                mappedTokens.put(
                        currentParameter,
                        String.join(" ", collectedTokens)
                );

                currentParameter = token.substring(1);
                collectedTokens = new ArrayList<>();
            } else {
                collectedTokens.add(token);
            }
        }

        mappedTokens.put(
                currentParameter,
                String.join(" ", collectedTokens)
        );

        removeEmptyStrings(mappedTokens);

        return mappedTokens;
    }

    private static void removeEmptyStrings(Map<String, String> map) {
        for (String key : map.keySet()) {
            if (map.get(key).equals("")) {
                map.put(key, null);
            }
        }
    }

    public static Map<String, String> parameterizeWithoutCommand(String input) {
        return parameterize("dummy " + input);
    }

}
