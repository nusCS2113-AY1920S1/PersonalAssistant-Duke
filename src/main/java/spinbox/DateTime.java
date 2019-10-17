package spinbox;

import com.joestelmach.natty.Parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public DateTime(String dateTime) {
        Parser parser = new Parser();
        this.dateTime = parser.parse(dateTime).get(0).getDates().get(0);
    }

    public DateTime(String dateTime, int index) {
        Parser parser = new Parser();
        this.dateTime = parser.parse(dateTime).get(0).getDates().get(index);
    }

    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Return the day of the month.
     * @return day of the month
     */
    public int getDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Return the day of the week.
     * @return day of the week
     */
    public int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Return hour.
     * @return hour
     */
    public int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Converts the Date object back to the string version in the format of MM/dd/yyyy HH:mm
     * This can be reused to create an identical dateTime object.
     * @return String equivalent of Date object.
     */
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return dateFormat.format(dateTime);
    }

    public boolean before(DateTime dateTime) {
        return this.dateTime.before(dateTime.getDateTime());
    }

    public boolean equals(DateTime dateTime) {
        return this.dateTime.equals(dateTime.getDateTime());
    }

    public boolean after(DateTime dateTime) {
        return this.dateTime.after(dateTime.getDateTime());
    }

    @Override
    public int compareTo(DateTime dateTimeTwo) {
        return this.getDateTime().compareTo(dateTimeTwo.getDateTime());
    }

    /**
     * Return another DateTime with date
     * set as start of the week relative to this DateTime.
     * @return start of the week
     */
    public DateTime getStartOfTheWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return new DateTime(calendar.getTime());
    }

    /**
     * Return another DateTime with date
     * set as end of the week relative to this DateTime.
     * @return end of the week
     */
    public DateTime getEndOfTheWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            calendar.add(Calendar.DATE, 1);
        }
        return new DateTime(calendar.getTime());
    }

    /**
     * Return another DateTime with date
     * set as start of the month relative to this DateTime.
     * @return start of the month
     */
    public DateTime getStartOfTheMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new DateTime(calendar.getTime());
    }

    /**
     * Return another DateTime with date
     * set as end of the month relative to this DateTime.
     * @return end of the month
     */
    public DateTime getEndOfTheMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return new DateTime(calendar.getTime());
    }
}
