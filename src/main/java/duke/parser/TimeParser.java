package duke.parser;

import duke.exception.DukeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;

/**
 * Parses the time in {@code String} type into the {@code Date} type.
 * The time string should follow the format: MM/dd/yyyy HHmm.
 */
public class TimeParser {
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HHmm");
    private static final int MILLISECONDS_IN_HOUR = 1000 * 60 * 60;
    private static final int MILLISECONDS_IN_MINUTE = 1000 * 60;
    private static final int MILLISECONDS_IN_SECOND = 1000;

    /**
     * Parses the time string into {@code Date} object.
     *
     * @param timeStr The time string to be converted into {@code Date} type.
     * @return The {@code Date} object.
     * @throws DukeException If time string has incorrect time format.
     */
    public static Date parse(String timeStr) throws DukeException {
        if (timeStr == null || timeStr.equals("null")) {
            return null;
        }

        try {
            return TIME_FORMAT.parse(timeStr);
        } catch (ParseException e) {
            throw new DukeException("☹ OOPS!!! Incorrect time format.");
        }
    }

    /**
     * Converts a date the corresponding string.
     * @param date the date to be converted into a {@code String}.
     * @return the string.
     */
    public static String format(Date date) {
        if (date == null) {
            return null;
        }

        return TIME_FORMAT.format(date);
    }

    /**
     * Converts a string containing a duration in a format such as 12h 4m 2s,
     * with spaces optional, to the corresponding number of milliseconds.
     * @param durationString the string to convert.
     * @return the amount of time in milliseconds described by the string.
     */
    public static long parseDuration(String durationString) {
        final String REGEX = "(?<=[a-zA-z])\\s*";

        // Split the string into hours, minutes, etc.
        String[] durationFields = durationString.split(REGEX);

        // Verify that the string contained only distinct units of time
        if (Arrays.stream(durationFields)
                .map(s -> s.substring(s.length() - 1))
                .distinct()
                .toArray().length != durationFields.length) {
            throw new DukeException("☹ OOPS!!! That's not a valid duration!");
        }

        long durationInMilliseconds = 0;
        for (String field : durationFields) {
            String unit = field.substring(field.length() - 1);
            long amount = Integer.parseInt(field.substring(0, field.length() - 1));

            switch (unit) {
            case "h":
                durationInMilliseconds += MILLISECONDS_IN_HOUR * amount;
                break;

            case "m":
                durationInMilliseconds += MILLISECONDS_IN_MINUTE * amount;
                break;

            case "s":
                durationInMilliseconds += MILLISECONDS_IN_SECOND * amount;
                break;

            default:
                throw new DukeException("☹ OOPS!!! I don't know what unit of time " + unit + " is!");
            }
        }

        return durationInMilliseconds;
    }

    /**
     * Converts a number of milliseconds into a string in the form xh ym zs,
     * where x, y and z are the amount of each time unit, unless x, y or z are 0,
     * in which case they are not included in the string.
     * @param durationInMilliseconds the duration in milliseconds to be converted.
     * @return the formatted string describing the number of hours, minutes and seconds.
     */
    public static String formatDuration(long durationInMilliseconds) {
        long hours = durationInMilliseconds / MILLISECONDS_IN_HOUR;
        long minutes = (durationInMilliseconds % MILLISECONDS_IN_HOUR) / MILLISECONDS_IN_MINUTE;
        long seconds = (durationInMilliseconds % MILLISECONDS_IN_MINUTE) / MILLISECONDS_IN_SECOND;
        StringJoiner durationStringBuilder = new StringJoiner(" ");

        if (hours > 0) {
            durationStringBuilder.add(Long.toString(hours) + "h");
        }

        if (minutes > 0) {
            durationStringBuilder.add(Long.toString(minutes) + "m");
        }

        if (seconds > 0) {
            durationStringBuilder.add(Long.toString(seconds) + "s");
        }

        return durationStringBuilder.toString();
    }
}
