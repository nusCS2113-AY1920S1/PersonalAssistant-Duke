package optix.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Formats input date to YYYY-MM-DD to be sorted in ShowMap.
 * Formats stored date to DD/MM/YYYY to increase readability.
 */
public class OptixDateFormatter {

    /**
     * Get correct String format for DateFormatter.
     * @param date Input date.
     * @return String prefix for DateFormatter.
     */
    private String getFormat(String date) {
        int padCount = 0;

        StringBuilder format = new StringBuilder();
        String[] timeType = {"d","M","y","H","H","m","m"};
        for (int i = 0; i < date.length(); i += 1) {
            char c = date.charAt(i);
            if (Character.isDigit(c)) {
                format.append(timeType[padCount]);
                if (padCount >= 3) { padCount += 1;}
            } else {
                format.append(c);
                padCount += 1;
            }
        }
        return format.toString();
    }

    /**
     * Format date from String to LocalDate.
     * @param dateString Input date.
     * @return LocalDate for the input date. Format: YYYY-MM-DD
     */
    public LocalDate toLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getFormat(dateString));
        //Convert string to localdate
        LocalDate localDate = LocalDate.parse(dateString,formatter);
        return localDate;
    }
}
