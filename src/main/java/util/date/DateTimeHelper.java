package util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeHelper {
    /**
     * This method takes in the date in String and return is as a Date object.
     * @param date String of the date
     * @return a Date object for consumption.
     * @throws ParseException throws when there is an error with parsing the date.
     */
    public Date formatDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(date);
    }

    /**
     * This method format the Date object and return as a String date.
     * @param date takes in the Date object.
     * @return a date String format for consumption.
     */
    public String formatDateForDisplay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        return formatter.format(date);
    }
}
