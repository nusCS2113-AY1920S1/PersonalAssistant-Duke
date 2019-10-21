package compal.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompalUtils {
    /**
     * Converts a date string to a Date object.
     *
     * @param dateStr The date string to be converted.
     * @return The date string in the form of a Date object.
     * @author Yue Jun Yi, yueyeah
     */
    public static Date stringToDate(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Converts a Date object to a date string. Correct type for creating a Task object.
     *
     * @param date The date in the form of a Date object.
     * @return The date in the form of a String object.
     * @author Yue Jun Yi, yueyeah
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }
}
