package dolla;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * duke.Time is a class that handles all time related methods.
 */
public class Time {

    /**
     * Returns a LocalDateTime variable converted from the specified String timeStr that
     * was in the format 'dd/MM/yyyy HHmm'.
     * @param timeStr String to be converted into DateTimeFormatter type.
     * @return A LocalDateTime variable that the computer can understand as time.
     */
    public static LocalDateTime readDateTime(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
        LocalDateTime time = LocalDateTime.parse(timeStr, formatter);
        return time;
    }

    /**
     * Returns a LocalDateTime variable converted from the specified String timeStr that
     * was in the format 'dd/MM/yyyy'.
     * @param timeStr String to be converted into DateTimeFormatter type.
     * @return A LocalDateTime variable that the computer can understand as time.
     */
    public static LocalDate readDate(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate time = LocalDate.parse(timeStr, formatter);
        return time;
    }

    /**
     * Converts the date from LocalDate to string.
     * @param date in LocalDate format
     * @return dateStr in "dd/MM/yyyy" format
     */
    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        String dateStr = date.format(formatter);
        return dateStr;
    }

    /**
     * converts the time from LocalDateTime to string.
     * @param time in LocalDateTime format
     * @return timeStr in "dd/MM/yyyy HHmm" format
     */
    public static String dateTimeToString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
        String timeStr = time.format(formatter);
        return timeStr;
    }
}
