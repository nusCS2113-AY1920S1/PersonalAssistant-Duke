package duchess.parser;

import duchess.exceptions.DuchessException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private static final String INVALID_FORMAT_MESSAGE = "Please enter dates in the format dd/mm/yyyy hhmm";
    private static final String INVALID_DATE_FORMAT_MESSAGE = "Please enter date in the format dd/mm/yyyy";
    private static final String padTiming = " 0000";
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
     * Parses a given string and returns a {@code LocalDate} object.
     *
     * @param date the string to parse
     * @return {@code LocalDate} obtained by parsing the supplied string
     * @throws DuchessException the supplied string is formatted incorrectly
     */
    public static LocalDate parseDate(String date) throws DuchessException {
        try {
            return LocalDate.parse(date + padTiming, formatter);
        } catch (DateTimeParseException e) {
            throw new DuchessException(INVALID_DATE_FORMAT_MESSAGE);
        }
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

        for (String token : List.of(input.split(" "))) {
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
