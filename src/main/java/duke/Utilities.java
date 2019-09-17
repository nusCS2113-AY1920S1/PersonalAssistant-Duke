package duke;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    /**
     * Formats String variable to Date by parsing
     * @param date in String format, derived from user input
     * @return null
     */
    public static Date formatStringToDate(String date) {
        Date formatted;
        try {
            DateFormat ft = new SimpleDateFormat("d/MM/yyyy HHmm");
            formatted = ft.parse(date);
            return formatted;
        }
        catch (ParseException e) {
            System.out.println("Invalid date format!");
        }
        return null;
    }

    /**
     * Formats Date variable to String by parsing
     * @param date in Date format, derived from Task object
     * @return formatted String variable
     */
    public static String formatDateToString(Date date) {
        // Format to Print: 2nd of December 2019, 6pm
        DateFormat df = new SimpleDateFormat ("d/MM/yyyy HHmm");
        return df.format(date);
    }
}
