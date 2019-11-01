package dolla;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Time {

    /**
     * Returns a LocalDateTime variable converted from the specified String timeStr that
     * was in the format 'dd/MM/yyyy HHmm'.
     * @param timeStr String to be converted into DateTimeFormatter type.
     * @return A LocalDateTime variable that the computer can understand as time.
     */
    public static LocalDateTime readDateTime(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
        return LocalDateTime.parse(timeStr, formatter);
    }

    /**
     * Returns a LocalDateTime variable converted from the specified String timeStr that
     * was in the format 'dd/MM/yyyy'.
     * @param timeStr String to be converted into DateTimeFormatter type.
     * @return A LocalDateTime variable that the computer can understand as time.
     */
    public static LocalDate readDate(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        return LocalDate.parse(timeStr, formatter);
    }

    /**
     * Converts the date from LocalDate to string.
     * @param date in LocalDate format
     * @return dateStr in "dd/MM/yyyy" format
     */
    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        return date.format(formatter);
    }

    /**
     * converts the time from LocalDateTime to string.
     * @param time in LocalDateTime format
     * @return timeStr in "dd/MM/yyyy HHmm" format
     */
    public static String dateTimeToString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
        return time.format(formatter);
    }
}
