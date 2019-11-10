package spinbox;

import com.joestelmach.natty.Parser;
import spinbox.exceptions.DateFormatException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class DateTime implements Comparable<DateTime> {
    private static final String[] NATURAL_LANGUAGE_WORDS = {"today", "tomorrow", "next", "yesterday", "day", "after"};
    private Date dateTime;

    /**
     * Constructor for simple DateTime object.
     * @param dateTime A Date Object with date and time.
     */
    public DateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Constructor for DateTime object from a String.
     * @param dateTime dateTime String.
     * @throws DateFormatException Date formatting exception.
     */
    public DateTime(String dateTime) throws DateFormatException {
        validateDateTime(dateTime);
        Parser parser = new Parser();
        this.dateTime = parser.parse(dateTime).get(0).getDates().get(0);
    }

    /**
     * Contructor for DateTime object from a String with more than
     * one date and time.
     * @param dateTime date and time string.
     * @param index index showing where the date and time should be.
     * @throws DateFormatException Date formatting exception
     */
    public DateTime(String dateTime, int index) throws DateFormatException {
        validateDateTime(extractDateTimeFromIndex(dateTime, index));
        Parser parser = new Parser();
        this.dateTime = parser.parse(dateTime).get(0).getDates().get(index);
    }

    public Date getDateTime() {
        return dateTime;
    }

    private String extractDateTimeFromIndex(String dateTime, int index) throws DateFormatException {
        String[] dateTimeArray = dateTime.split(" to ");

        if (dateTimeArray.length != 2) {
            throw new DateFormatException("You input:"
                    + dateTime
                    + " Format should be at: MM/dd/yyyy HH:mm to MM/dd/yyyy HH:mm");
        }

        return dateTimeArray[index];
    }

    /**
     * Checks if there is any words for natty to parse,
     * if not validate the date and time input.
     * @param dateTime date and time string.
     */
    private void validateDateTime(String dateTime) throws DateFormatException {
        for (String word : NATURAL_LANGUAGE_WORDS) {
            if (dateTime.contains(word)) {
                return;
            }
        }

        dateTime = dateTime.trim();
        String[] dateTimeArray = dateTime.split(" ");
        dateTimeArray = Arrays.stream(dateTimeArray).filter(s -> !s.isEmpty()).toArray(String[]::new);

        if (dateTimeArray.length != 2) {
            throw new DateFormatException("DateTime: " + dateTime + " is invalid."
                    + " Please provide both date and time in the format of MM/dd/yyyy HH:mm!");
        }

        validateDate(dateTimeArray[0]);
        validateTime(dateTimeArray[1]);
    }

    private void  validateDate(String date) throws DateFormatException {
        int[] numberOfDaysEachMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new DateFormatException("Date must be in MM/dd/yyyy format.");
        }

        String[] dateArray = date.split("/");
        int month = Integer.parseInt(dateArray[0]);
        int day = Integer.parseInt(dateArray[1]);
        int year = Integer.parseInt(dateArray[2]);

        if (checkLeapYear(year)) {
            numberOfDaysEachMonth[1] = 29;
        }

        if (month > 12 || month < 1) {
            throw new DateFormatException("Month must be from 1 - 12 range.");
        }

        int maxDay = numberOfDaysEachMonth[month - 1];

        if (day < 1 || day > maxDay) {
            throw new DateFormatException("Day must be from 1 - " + maxDay + " range for this month and year.");
        }
    }

    private boolean checkLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }

        if (year % 100 == 0) {
            return false;
        }

        if (year % 4 == 0) {
            return true;
        }

        return false;
    }

    private void validateTime(String time) throws DateFormatException {
        if (!time.matches("\\d{2}:\\d{2}")) {
            throw new DateFormatException("Time must be in HH:mm format.");
        }

        String[] timeArray = time.split(":");
        int hours = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);

        if (hours > 23 || hours < 0) {
            throw new DateFormatException("Hour must be from 0 - 23 range.");
        }

        if (minutes > 59 || minutes < 0) {
            throw new DateFormatException("Minutes must be from 0 - 59 range");
        }
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

    public String getMonthString() {
        DateFormat dateFormat = new SimpleDateFormat("MMMMM");
        return dateFormat.format(dateTime);
    }

    public String getYearString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        return dateFormat.format(dateTime);
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

    /**
     * Return another DateTime with date
     * set as next day relative to this DateTime.
     * @return next day
     */
    public DateTime getNextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.add(Calendar.DATE, 1);
        return new DateTime(calendar.getTime());
    }

    /**
     * Return another DateTime with date
     * set as start of the day relative to this DateTime.
     * @return start of the day
     */
    public DateTime getStartOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);

        return new DateTime(calendar.getTime());
    }

    /**
     * Return another DateTime with date
     * set as end of day relative to this DateTime.
     * @return end of the day
     */
    public DateTime getEndOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTime);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return new DateTime(calendar.getTime());
    }
}
