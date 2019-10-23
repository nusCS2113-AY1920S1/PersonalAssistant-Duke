package duke.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Convert between String and Date.
 */
public class Convert {

    /**
     * Returns the suffix to be used after the days in the Date, useful for printing the Date in the desired format.
     *
     * @param n indication the Day of the month
     * @return the suffix accordingly to the day of the month needed
     */
    public static String getDaySuffix(int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    /**
     * Returns a {@link Date} representation of a String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy".
     *
     * @param date String in the format "dd/MM/yyyy hhmm" or "dd/MM/yyyy", used to extract a {@link Date} instance from
     * @return The {@link Date} instance created from the argument string or null
     */
    public static Date stringToDate(String date) {
        DateFormat formatter;
        if (date.length() > 11) {
            formatter = new SimpleDateFormat("dd/MM/yyyy hhmm");
        } else {
            formatter = new SimpleDateFormat("dd/MM/yyyy");
        }
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            System.out.println("Warning: Unable to convert \"" + date + "\" to a Date.");
            return null;
        }
    }


    /**
     * Returns the {@link Date } instance as a String to be printed in the file.
     *
     * @param date deadline {@link Date} for finishing the task
     * @return String the date for the deadline
     */
    public static String getDateString(Date date, String dateString) {
        if (date == null) {
            return dateString;
        }
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String pattern;
        if (dateString.length() > 11) {
            pattern = "d'" + getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy, ha ";
        } else {
            pattern = "d'" + getDaySuffix(localDate.getDayOfMonth()) + "' 'of' MMMM yyyy";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
