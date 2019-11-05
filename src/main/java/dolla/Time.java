package dolla;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Time {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Returns a LocalDateTime variable converted from the specified String timeStr that
     * was in the format 'dd/MM/yyyy'.
     * @param timeStr String to be converted into DateTimeFormatter type.
     * @return A LocalDateTime variable that the computer can understand as time.
     */
    public static LocalDate readDate(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.ENGLISH);
        return LocalDate.parse(timeStr, formatter);
    }

    /**
     * Converts the date from LocalDate to string.
     * @param date in LocalDate format
     * @return dateStr in "dd/MM/yyyy" format
     */
    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.ENGLISH);
        return date.format(formatter);
    }
}
