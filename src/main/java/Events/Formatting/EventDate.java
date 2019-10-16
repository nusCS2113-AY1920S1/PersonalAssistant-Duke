package Events.Formatting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Model_Class.DateObj object stores the input date and time as a java object.
 */

public class EventDate {

    protected String userInputDateString;

    protected Date javaDate;

    protected String formattedDateString;

    protected int STRING_TO_DATE = 1;

    protected int DATE_TO_STRING = 2;

    /**
     * Creates a custom "date object" for string to date.
     */
    public EventDate(String userInputDateString) {
        this.userInputDateString = userInputDateString;
        formatDate(STRING_TO_DATE);
    }

    /**
     * Creates a custom "date object" for date to string (used for recurring events).
     */
    public EventDate(Date dateClass) {
        this.javaDate = dateClass;
        formatDate(DATE_TO_STRING);
    }

    /**
     * Takes in an identifier and performs the corresponding actions.
     *
     * @param identifier
     */
    private void formatDate(int identifier) {
        if (identifier == STRING_TO_DATE) { //convert user input to java date and store both.
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HHmm");
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.ENGLISH);
                dateFormat.setLenient(false);
                this.javaDate = dateFormat.parse(userInputDateString);
                this.formattedDateString = formatter.format(javaDate);
                this.userInputDateString = dateFormat.format(javaDate);
            } catch (ParseException pe) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);
                    dateFormat.setLenient(false);
                    this.javaDate = dateFormat.parse(userInputDateString);
                    this.formattedDateString = formatter.format(javaDate);
                    this.userInputDateString = dateFormat.format(javaDate);
                } catch (ParseException pe2) {
                    this.formattedDateString = userInputDateString;
                }
            }

        } else if (identifier == DATE_TO_STRING) { //convert date object to input string format and store both.
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy, HH:mm", Locale.ENGLISH);
            this.formattedDateString = formatter.format(javaDate);
            formatter = new SimpleDateFormat("dd-MM-yyyy HHmm");
            this.userInputDateString = formatter.format(javaDate);
        }
    }

    /**
     * Gets the current java Date object.
     */
    public Date getCurrentJavaDate() {
        javaDate = new Date();
        return javaDate;
    }

    public String getUserInputDateString() {
        return userInputDateString;
    }

    public String getFormattedDateString() {
        return this.formattedDateString;
    }

    /**
     * Gets the Event java Date object
     */
    public Date getEventJavaDate() {
        return this.javaDate;
    }

    /**
     * Compares this dateObj with another input dateObj
     * If this == other, return 0.
     * If this < other, return -1.
     * If this > other, return 1.
     * If the two EventDates cannot be compared as either one of them stores the date as a string, return 2.
     *
     * @param other the input dateObj used for the comparison
     * @return Output the result of the comparison according to the algorithm stated above.
     */
    public int compare(EventDate other) {
        if (javaDate == null || other.getEventJavaDate() == null) {
            return 2;
        } else {
            Date otherDate = other.getEventJavaDate();
            if (javaDate.compareTo(otherDate) > 0) {
                return 1;
            } else if (javaDate.compareTo(otherDate) == 0) {
                return 0;
            } else if (javaDate.compareTo(otherDate) < 0) {
                return -1;
            }
        }
        return 2;
    }

    /**
     * Adds n days to the javaDate object.
     *
     * @param noOfDays numbers of days to add
     */
    public void addDaysAndSetMidnight(int noOfDays) {
        Calendar c = Calendar.getInstance();

        //sets calender class date to midnight.
        c.add(Calendar.DATE, noOfDays);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);

        this.javaDate = c.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        this.userInputDateString = formatter.format(javaDate);
        formatter = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.ENGLISH);
        this.formattedDateString = formatter.format(javaDate);
    }
}
