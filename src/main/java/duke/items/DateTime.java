package duke.items;

import duke.exceptions.BadInputException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
//TODO: Import an existing datetime class or write a better one.
/**
 * Stores date and time information by field - day, month, year, hour, minute.
 * Allows printing of a fancy formatted date.
 * Uses a boolean variable to indicate if the date object has been properly initialised.
 */

public class DateTime {
    private String dateAndTime;
    private boolean valid;
    private Date at;

    /**
     * DateTime constructor. Converts input string into attributes of the date and time.
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

            this.at = calendar.getTime();
        } catch (Exception e) {
            throw new BadInputException("Improper datetime. Correct format: dd/mm/yyyy hhmm.\nEnter task again.");
        }
    }

    /**
     * Returns the Date in the DateTime object.
     * @return Time in Calendar-Date format.
     */
    public Date getAt() {
        return at;
    }

    /**
     * Sets the calendar date.
     * @param date to be set.
     */
    public void setDate(Date date) {
        at = (date);
    }

    /**
     * Returns the date in a friendlier format.
     */
    public String returnFormattedDate() {
        return ("" + at);
    }

}
