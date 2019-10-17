package optix.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Formats input date to YYYY-MM-DD to be sorted in ShowMap.
 */
public class OptixDateFormatter {

    /**
     * Get correct String format for DateFormatter.
     *
     * @param date Input date.
     * @return String prefix for DateFormatter.
     */
    private String getFormat(String date) {
        int padCount = 0;

        StringBuilder format = new StringBuilder();
        String[] timeType = {"d", "M", "y", "H", "H", "m", "m"};
        for (int i = 0; i < date.length(); i += 1) {
            char c = date.charAt(i);
            if (Character.isDigit(c)) {
                format.append(timeType[padCount]);
                if (padCount >= 3) {
                    padCount += 1;
                }
            } else {
                format.append(c);
                padCount += 1;
            }
        }
        return format.toString();
    }

    /**
     * Format date from String to LocalDate.
     *
     * @param dateString Input date.
     * @return LocalDate for the input date. Format: YYYY-MM-DD
     */
    public LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getFormat(dateString));
        //Convert string to localdate
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * Formate date from LocalDate to String.
     * @param localDate YYYY-MM-DD.
     * @return String for the date. Format: DD/MM/YYYY.
     */
    public String toStringDate(LocalDate localDate) {
        String[] splitStr = localDate.toString().split("-", 3);

        return String.format("%s/%s/%s", splitStr[2], splitStr[1], splitStr[0]);
    }

    /**
     * Checks if date given exists in calendar.
     *
     * @param date String input of the date.
     * @return {@code true} date can be found in the calendar
     * {@code false} otherwise
     */
    public boolean isValidDate(String date) {
        String[] splitStr = date.split("/");
        int yr = Integer.parseInt(splitStr[2]);
        int mth = Integer.parseInt(splitStr[1]);
        int dy = Integer.parseInt(splitStr[0]);

        if (mth == 2) {
            return (isLeap(yr) && dy <= 29) || (!isLeap(yr) && dy <= 28);
        } else if (mth == 4 || mth == 6 || mth == 9 || mth == 11) {
            return dy <= 30;
        } else if (mth == 1 || mth == 3 || mth == 5 || mth == 7 || mth == 8 || mth == 10 || mth == 12) {
            return dy <= 31;
        }
        return false;
    }

    /**
     * Check if it is a leap year.
     *
     * @param year to check whether its a leap year
     * @return {@code true} if it is a leap year
     * {@code false} otherwise
     */
    private boolean isLeap(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    /**
     * Get the first day of the month in query.
     *
     * @param year The year in query.
     * @param month The month in query.
     * @return The first day of the month in LocalDate.
     */
    public LocalDate getStartOfMonth(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    /**
     * Get the first day of the following month for the month in query.
     *
     * @param year The year in query.
     * @param month The month in query.
     * @return The first day of the following month for the month in query in LocalDate.
     */
    public LocalDate getEndOfMonth(int year, int month) {
        return LocalDate.of(year, month, 1).plusMonths(1);
    }
}
