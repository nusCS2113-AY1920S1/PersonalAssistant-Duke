package eggventory.model.items;

import eggventory.commons.exceptions.BadInputException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Stores date and time information by field - day, month, year, hour, minute.
 * Allows printing of a fancy formatted date.
 * Uses a boolean variable to indicate if the date object has been properly initialised.
 */

public class DateTime {
    private String dateAndTime;
    private boolean valid;
    private Calendar calendar;

    /**
     * DateTime constructor. Converts input string into attributes of the date and time.
     *
     * @param dateAndTime String representing date and time in format of "DD/MM/YYYY HHMM"
     */
    public DateTime(String dateAndTime) throws BadInputException {
        this.dateAndTime = dateAndTime;
        Calendar calendar = Calendar.getInstance();
        try {
            String[] dateTimeToken = dateAndTime.split(" ", 2);
            String[] dateToken = dateTimeToken[0].split("/");
            String time = dateTimeToken[1];

            calendar.set(Calendar.DATE, Integer.parseInt(dateToken[0])); //day of the month
            calendar.set(Calendar.MONTH, Integer.parseInt(dateToken[1]) - 1); // jan is 0, dec is 11
            calendar.set(Calendar.YEAR, Integer.parseInt(dateToken[2]));

            // set with the first 2 elements - the hour
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time) / 100);
            // set with the last 2 elements, the minutes
            calendar.set(Calendar.MINUTE, Integer.parseInt(time) % 100);
            // set seconds to be 0 by default
            calendar.set(Calendar.SECOND, 0);

            this.calendar = calendar;
        } catch (Exception e) {
            throw new BadInputException("Improper datetime. Correct format: dd/mm/yyyy hhmm.\nEnter task again.");
        }
    }

    /**
     * Returns the Date in the DateTime object.
     * @return Time in Calendar-Date format.
     */
    public Calendar getAt() {
        return calendar;
    }

    //    /**
    //     * Sets the calendar date.
    //     * @param date to be set.
    //     */
    //    public void setDate(Date date) {
    //        at = (date);
    //    }


    /**
     * Returns string of the stored date in the default Date.java format.
     */
    public String returnFormattedDate() {
        SimpleDateFormat formatted = new SimpleDateFormat("EEE MMM d yyyy K:mm a");
        String output = formatted.format(calendar.getTime());
        return (output);
    }

    /**
     * Datetime in the format of "dd/MM/yyyy HHmm".
     * @return
     */
    public String toString() {
        SimpleDateFormat formatted = new SimpleDateFormat("dd/MM/yyyy HHmm");
        return formatted.format(calendar.getTime());
    }

}
