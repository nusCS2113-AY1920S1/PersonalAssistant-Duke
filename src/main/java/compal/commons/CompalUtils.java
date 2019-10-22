package compal.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CompalUtils {
    //@@author yueyeah
    /**
     * Converts a date string to a Date object.
     *
     * @param dateStr The date string to be converted.
     * @return The date string in the form of a Date object.
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

    //@@author yueyeah
    /**
     * Converts a Date object to a date string. Correct type for creating a Task object.
     *
     * @param date The date in the form of a Date object.
     * @return The date in the form of a String object.
     */
    public static String dateToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

    //@@author yueyeah
    /**
     * Finds out if a start time is before an end time. If the start time is after end time,
     * it is likely that the end time is on the next day.
     *
     * @param startTimeString The start time in the form of a String object.
     * @param endTimeString The end time in the form of a String object.
     * @return True if the start time is before the end time, False if not.
     */
    public static boolean isTimeInSequence(String startTimeString, String endTimeString) {
        int startTimeInt = Integer.valueOf(startTimeString);
        int endTimeInt = Integer.valueOf(endTimeString);
        return (startTimeInt < endTimeInt);
    }
}
