package duke.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * DateParser class to handle date string and object parsing.
 * @author HashirZahir
 */

public class DateParser {
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HHmm");

    /**
     * Parse the date given in string format and get back Java Date object.
     * @param dateTimeStr Date as a string following format shown in class constructor.
     * @return Java Date object of the input date string.
     */
    public static Date parseDate(String dateTimeStr) {
        Date date;
        SimpleDateFormat dateDayOnlyFormatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = dateDayOnlyFormatter.parse(dateTimeStr);
        } catch (ParseException pe) {
            // if there is error, save as current date. TODO: Better error resolution
            System.out.println(pe);
            date = new Date();
        }

        return date;
    }

    /**
     * Parse input as period string and return a Date object.
     * @param periodStr input period string.
     * @return period object containing the periodic duration of event. 0 days implies non-recurring event.
     */
    public static Period parsePeriod(String periodStr) {
        // Format of input string: "P1Y2M21D" which implies 1 Year, 2 Months, 21 Days

        Period period;

        if (periodStr.isBlank()) {
            period = Period.of(0,0,0);
        } else {
            try {
                period = Period.parse(periodStr);
            } catch (DateTimeParseException pe) {
                System.out.println(pe);
                System.out.println("Incorrect format of recurring event, recurring format argument to be ignored");
                period = Period.of(0,0,0);
            }
        }


        return period;
    }

    public static boolean isSameDayMonthYear(Date date1, Date date2) {

        if (date1.getDay() == date2.getDay() && date1.getMonth() == date2.getMonth()
                && date1.getYear() == date2.getYear()) {
            return true;
        }

        return false;
    }

    /**
     * Custom string representation of Java Date object.
     * @param date Input Java Date object.
     * @return String representation of Java Date object.
     */
    public static String getDateStr(Date date) {
        return dateFormatter.format(date);
    }
}
