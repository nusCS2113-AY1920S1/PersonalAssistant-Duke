package duke;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime implements Comparable<DateTime> {
    private Date dateTime;

    /**
     * Constructor for simple dateTime object.
     * @param dateTime A Date Object with date and time.
     */
    public DateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Converts the Date object back to the string version in the format of MM/dd/yyyy HH:mm
     * This can be reused to create an identical dateTime object.
     * @return String equivalent of Date object.
     */
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String dateString = dateFormat.format(dateTime);
        return dateString;
    }

    @Override
    public int compareTo(DateTime dateTimeTwo) {
        return this.getDateTime().compareTo(dateTimeTwo.getDateTime());
    }
}
