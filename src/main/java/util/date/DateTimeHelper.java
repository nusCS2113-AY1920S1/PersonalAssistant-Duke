package util.date;

import util.log.ArchDukeLogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

//@@author DKenobi
public class DateTimeHelper {
    private Calendar calender = Calendar.getInstance(TimeZone.getTimeZone("GMT + 8:00"));

    /**
     * This method takes in the date in String and return is as a Date object.
     * @param date String of the date
     * @return a Date object for consumption.
     * @throws ParseException throws when there is an error with parsing the date.
     */
    public Date formatDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        return formatter.parse(date);
    }

    /**
     * This method format the Date object and return as a String date.
     * @param date takes in the Date object.
     * @return a date String format for consumption.
     */
    public String formatDateForDisplay(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        formatter.setLenient(false);
        return formatter.format(date);
    }

    /**
     * This method calculate the difference from the current date to the task set date.
     * @param taskDate The deadline of the task.
     * @return the remaining/Overdue day(s) of the days.
     */
    public String getDifferenceDays(Date taskDate) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        Date currentDate = new Date();
        try {
            currentDate = formatter.parse(formatter.format(currentDate));
        } catch (ParseException e) {
            ArchDukeLogger.logDebug(DateTimeHelper.class.getName(), e.getMessage());
        }
        long diff = currentDate.getTime() - taskDate.getTime();
        long totalDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if (totalDays == 0) {
            return " (Due: Today)";
        } else if (totalDays < 0) {
            return " (Remaining: " + Math.abs(totalDays) + " Days)";
        } else {
            return " (Overdue: " + totalDays + " Days)";
        }
    }

    //@@author Lucria
    public String getCurrentYear() {
        return Integer.toString(this.calender.get(Calendar.YEAR));
    }

    public String getCurrentMonth() {
        return Integer.toString(this.calender.get(Calendar.MONTH) + 1);
    }

    public String getCurrentDate() {
        return Integer.toString(this.calender.get(Calendar.DAY_OF_MONTH) + 1);
    }

    public int getDayAtStartOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public String getMonthFromDateObject(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return Integer.toString(cal.get(Calendar.MONTH));
    }

    public String getDayFromDateObject(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
    }
}
